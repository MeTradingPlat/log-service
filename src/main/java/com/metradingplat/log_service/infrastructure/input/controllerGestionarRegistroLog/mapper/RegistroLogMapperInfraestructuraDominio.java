package com.metradingplat.log_service.infrastructure.input.controllerGestionarRegistroLog.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.metradingplat.log_service.domain.models.RegistroLog;
import com.metradingplat.log_service.infrastructure.input.controllerGestionarRegistroLog.DTOAnswer.RegistroLogDTORespuesta;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistroLogMapperInfraestructuraDominio {
    RegistroLogDTORespuesta mappearDeRegistroLogARespuesta(RegistroLog registroLog);

    List<RegistroLogDTORespuesta> mappearListaDeRegistroLogARespuesta(List<RegistroLog> registrosLog);
}
