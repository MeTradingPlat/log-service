package com.metradingplat.log_service.infrastructure.input.websocketGestionarLogs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.metradingplat.log_service.infrastructure.input.websocketGestionarLogs.handler.RegistroLogWSHandler;

import lombok.RequiredArgsConstructor;

/**
 * Endpoint interno (servicio a servicio, sin pasar por el Gateway) que
 * reemplaza al @KafkaListener de topic "logs".
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class InternalWebSocketConfig implements WebSocketConfigurer {

    private final RegistroLogWSHandler registroLogWSHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.registroLogWSHandler, "/ws/internal/logs");
    }
}
