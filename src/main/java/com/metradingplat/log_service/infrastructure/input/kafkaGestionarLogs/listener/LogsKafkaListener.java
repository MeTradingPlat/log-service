package com.metradingplat.log_service.infrastructure.input.kafkaGestionarLogs.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.metradingplat.log_service.application.input.GestionarRegistroLogCUIntPort;
import com.metradingplat.log_service.domain.models.RegistroLog;
import com.metradingplat.log_service.infrastructure.input.kafkaGestionarLogs.DTOPetition.RegistroLogDTOPeticion;
import com.metradingplat.log_service.infrastructure.input.kafkaGestionarLogs.mappers.RegistroLogKafkaMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LogsKafkaListener {

    private final GestionarRegistroLogCUIntPort objGestionarRegistroLogCUInt;
    private final RegistroLogKafkaMapper objMapper;

    @KafkaListener(topics = "logs", groupId = "logs-group")
    public void recibirLog(RegistroLogDTOPeticion command) {
        log.debug("Recibido log de {}: [{}] {}", command.getServicioOrigen(),
                command.getNivel(), command.getMensaje());
        RegistroLog objRegistroLog = this.objMapper.deDTOADominio(command);
        this.objGestionarRegistroLogCUInt.registrarLog(objRegistroLog);
    }
}
