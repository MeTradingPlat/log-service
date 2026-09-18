package com.metradingplat.log_service.infrastructure.output.websocket.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metradingplat.log_service.application.output.PublicarLogIntPort;
import com.metradingplat.log_service.domain.models.RegistroLog;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

/**
 * Reemplaza al KafkaTemplate.send(TOPIC_LOGS_NOTIFICATIONS, ...) --
 * mantiene una conexion WS saliente persistente hacia notification-service,
 * reconectando sola si se cae (misma idea que RealtimeCandleClient del lado
 * de signal-processing-service). Un envio con la conexion caida simplemente
 * se descarta (mismo best-effort que ya tenia Kafka: retries=3 y despues
 * loguear el error, sin cola de reintento persistente).
 */
@Service
@Slf4j
public class LogWebSocketProducerAdapter implements PublicarLogIntPort {

    private static final int RECONNECT_DELAY_SECONDS = 5;

    private final String wsUrl;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "log-ws-notif-reconnect");
        t.setDaemon(true);
        return t;
    });

    private volatile WebSocket webSocket;
    private volatile boolean shuttingDown = false;

    public LogWebSocketProducerAdapter(@Value("${app.notification-service.ws-url}") String wsUrl,
            ObjectMapper objectMapper) {
        this.wsUrl = wsUrl;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void connect() {
        this.httpClient.newWebSocketBuilder()
                // El Gateway exige este header en todas las rutas internas
                // (GatewayHeaderFilter) -- sin el, el handshake devuelve 403
                // antes de abrir el socket. Mismo requisito que ya usa
                // marketdata-service para sus rutas WS.
                .header("X-Gateway-Passed", "true")
                .buildAsync(URI.create(this.wsUrl), new ReconnectingListener())
                .whenComplete((ws, error) -> {
                    if (error != null) {
                        log.warn("No se pudo conectar a notification-service via WS ({}): {}",
                                this.wsUrl, error.getMessage());
                        this.scheduleReconnect();
                        return;
                    }
                    this.webSocket = ws;
                    log.info("Conectado a notification-service via WS: {}", this.wsUrl);
                });
    }

    @PreDestroy
    void shutdown() {
        this.shuttingDown = true;
        this.scheduler.shutdownNow();
        if (this.webSocket != null) {
            this.webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "shutdown");
        }
    }

    private void scheduleReconnect() {
        if (this.shuttingDown) {
            return;
        }
        this.scheduler.schedule(this::connect, RECONNECT_DELAY_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public void publicarLogParaNotificaciones(RegistroLog objRegistroLog) {
        WebSocket ws = this.webSocket;
        if (ws == null || ws.isOutputClosed()) {
            log.warn("WS a notification-service no disponible, se descarta notificacion: [{}] {}",
                    objRegistroLog.getNivel(), objRegistroLog.getMensaje());
            return;
        }
        try {
            Map<String, Object> dto = new HashMap<>();
            dto.put("idRegistroLog", objRegistroLog.getIdRegistroLog());
            dto.put("servicioOrigen", objRegistroLog.getServicioOrigen());
            dto.put("nivel", objRegistroLog.getNivel());
            dto.put("mensaje", objRegistroLog.getMensaje());
            dto.put("idEscaner", objRegistroLog.getIdEscaner());
            dto.put("symbol", objRegistroLog.getSymbol());
            dto.put("categoria", objRegistroLog.getCategoria());
            dto.put("timestamp", objRegistroLog.getTimestamp());
            dto.put("metadatos", objRegistroLog.getMetadatos());

            String json = this.objectMapper.writeValueAsString(dto);
            ws.sendText(json, true);
            log.debug("Log publicado via WS a notification-service: [{}] {}",
                    objRegistroLog.getNivel(), objRegistroLog.getMensaje());
        } catch (Exception e) {
            log.error("Fallo publicando log via WS a notification-service: {}", e.getMessage());
        }
    }

    private class ReconnectingListener implements WebSocket.Listener {
        @Override
        public void onOpen(WebSocket webSocket) {
            WebSocket.Listener.super.onOpen(webSocket);
        }

        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            log.warn("WS a notification-service cerrado (status={}, reason={}), reintentando en {}s",
                    statusCode, reason, RECONNECT_DELAY_SECONDS);
            LogWebSocketProducerAdapter.this.webSocket = null;
            LogWebSocketProducerAdapter.this.scheduleReconnect();
            return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            log.warn("Error en WS a notification-service: {}, reintentando en {}s",
                    error.getMessage(), RECONNECT_DELAY_SECONDS);
            LogWebSocketProducerAdapter.this.webSocket = null;
            LogWebSocketProducerAdapter.this.scheduleReconnect();
        }
    }
}
