package com.metradingplat.log_service.application.input;

import java.util.List;

import com.metradingplat.log_service.domain.models.RegistroLog;

public interface GestionarRegistroLogCUIntPort {
    RegistroLog registrarLog(RegistroLog objRegistroLog);

    List<RegistroLog> listar();

    RegistroLog obtenerPorId(Long idRegistroLog);

    List<RegistroLog> obtenerPorServicio(String servicioOrigen);

    List<RegistroLog> obtenerPorEscaner(Long idEscaner);
}
