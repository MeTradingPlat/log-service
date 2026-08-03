package com.metradingplat.log_service.application.input;

import java.time.LocalDate;
import java.util.List;

import com.metradingplat.log_service.domain.models.RegistroLog;

public interface GestionarRegistroLogCUIntPort {
    RegistroLog registrarLog(RegistroLog objRegistroLog);

    List<RegistroLog> listar();

    RegistroLog obtenerPorId(Long idRegistroLog);

    List<RegistroLog> obtenerPorServicio(String servicioOrigen);

    List<RegistroLog> obtenerPorEscaner(Long idEscaner);
    List<RegistroLog> obtenerPorEscaner(Long idEscaner, int page, int size);

    void eliminarPorEscaner(Long idEscaner);

    List<LocalDate> obtenerFechasSenial(Long idEscaner);

    List<RegistroLog> obtenerPorEscanerYFecha(Long idEscaner, LocalDate fecha, int page, int size);

    List<String> obtenerSimbolosSenializadosHoy(Long idEscaner);
}
