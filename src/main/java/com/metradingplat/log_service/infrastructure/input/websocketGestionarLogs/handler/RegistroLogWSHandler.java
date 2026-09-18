package com.metradingplat.log_service.infrastructure.input.websocketGestionarLogs.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metradingplat.log_service.application.input.GestionarRegistroLogCUIntPort;
import com.metradingplat.log_service.domain.models.RegistroLog;
import com.metradingplat.log_service.infrastructure.input.kafkaGestionarLogs.DTOPetition.RegistroLogDTOPeticion;
import com.metradingplat.log_service.infrastructure.input.kafkaGestionarLogs.mappers.RegistroLogKafkaMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Reemplaza al @KafkaListener de topic "logs" -- signal-processing-service y
 * scanner-management-service se conectan aca como clientes WS para mandar
 * cada evento (senal, log, o CLEAR_SCANNER_SIGNALS) a persistir.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RegistroLogWSHandler extends TextWebSocketHandler {

    private final GestionarRegistroLogCUIntPort objGestionarRegistroLogCUInt;
    private final RegistroLogKafkaMapper objMapper;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        RegistroLogDTOPeticion command = this.objectMapper.readValue(message.getPayload(),
                RegistroLogDTOPeticion.class);

        if ("CLEAR_SCANNER_SIGNALS".equals(command.getType()) && command.getIdEscaner() != null) {
            log.info("Clearing signals for scanner {}", command.getIdEscaner());
            this.objGestionarRegistroLogCUInt.eliminarPorEscaner(command.getIdEscaner());
            return;
        }
        log.debug("Recibido log via WS de {}: [{}] {}", command.getServicioOrigen(),
                command.getNivel(), command.getMensaje());
        RegistroLog objRegistroLog = this.objMapper.deDTOADominio(command);
        this.objGestionarRegistroLogCUInt.registrarLog(objRegistroLog);
    }
}
