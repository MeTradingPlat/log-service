package com.metradingplat.log_service.infrastructure.output.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.metradingplat.log_service.application.output.PublicarLogIntPort;
import com.metradingplat.log_service.domain.models.RegistroLog;
import com.metradingplat.log_service.infrastructure.output.kafka.DTO.RegistroLogNotificacionDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogKafkaProducerAdapter implements PublicarLogIntPort {

    private static final String TOPIC_LOGS_NOTIFICATIONS = "logs.notifications";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publicarLogParaNotificaciones(RegistroLog objRegistroLog) {
        RegistroLogNotificacionDTO dto = RegistroLogNotificacionDTO.builder()
                .idRegistroLog(objRegistroLog.getIdRegistroLog())
                .servicioOrigen(objRegistroLog.getServicioOrigen())
                .nivel(objRegistroLog.getNivel())
                .mensaje(objRegistroLog.getMensaje())
                .idEscaner(objRegistroLog.getIdEscaner())
                .symbol(objRegistroLog.getSymbol())
                .categoria(objRegistroLog.getCategoria())
                .timestamp(objRegistroLog.getTimestamp())
                .build();

        this.kafkaTemplate.send(TOPIC_LOGS_NOTIFICATIONS, dto);
        log.debug("Log publicado a {} para notificaciones: [{}] {}",
                TOPIC_LOGS_NOTIFICATIONS, objRegistroLog.getNivel(), objRegistroLog.getMensaje());
    }
}
