package com.metradingplat.log_service.infrastructure.output.persistence.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.metradingplat.log_service.domain.models.RegistroLog;
import com.metradingplat.log_service.infrastructure.output.persistence.entitys.RegistroLogEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistroLogMapperPersistencia {
    RegistroLog mappearDeEntityARegistroLog(RegistroLogEntity entity);

    RegistroLogEntity mappearDeRegistroLogAEntity(RegistroLog registroLog);

    List<RegistroLog> mappearListaDeEntityARegistroLog(List<RegistroLogEntity> entities);
}
