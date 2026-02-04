package com.metradingplat.log_service.infrastructure.input.kafkaGestionarLogs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.metradingplat.log_service.domain.models.RegistroLog;
import com.metradingplat.log_service.infrastructure.input.kafkaGestionarLogs.DTOPetition.RegistroLogDTOPeticion;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistroLogKafkaMapper {
    RegistroLog deDTOADominio(RegistroLogDTOPeticion dto);
}
