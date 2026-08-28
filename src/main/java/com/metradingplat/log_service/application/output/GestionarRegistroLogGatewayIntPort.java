package com.metradingplat.log_service.application.output;

import java.time.LocalDate;
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

    List<LocalDate> obtenerFechasSenial(Long idEscaner);

    List<RegistroLog> obtenerPorIdEscanerYFecha(Long idEscaner, LocalDate fecha, int page, int size);

    List<String> obtenerSimbolosSenializadosHoy(Long idEscaner);

    List<LocalDate> obtenerFechasRegistro(Long idEscaner);

    List<RegistroLog> obtenerPorIdEscanerYFechaTodas(Long idEscaner, LocalDate fecha, int page, int size);

    long contarPorIdEscanerYFecha(Long idEscaner, LocalDate fecha);

    long contarPorIdEscanerYFechaTodas(Long idEscaner, LocalDate fecha);
}
