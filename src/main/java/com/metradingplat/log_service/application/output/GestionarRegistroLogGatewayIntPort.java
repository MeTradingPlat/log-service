package com.metradingplat.log_service.application.output;

import java.util.List;

import com.metradingplat.log_service.domain.models.RegistroLog;

public interface GestionarRegistroLogGatewayIntPort {
    RegistroLog guardar(RegistroLog objRegistroLog);

    List<RegistroLog> obtenerTodos();

    RegistroLog obtenerPorId(Long idRegistroLog);

    Boolean existePorId(Long idRegistroLog);

    List<RegistroLog> obtenerPorServicioOrigen(String servicioOrigen);

    List<RegistroLog> obtenerPorIdEscaner(Long idEscaner);
    List<RegistroLog> obtenerPorIdEscaner(Long idEscaner, int page, int size);

    void eliminarPorIdEscaner(Long idEscaner);
}
