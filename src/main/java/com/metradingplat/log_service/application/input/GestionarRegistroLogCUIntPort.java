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

    List<LocalDate> obtenerFechasRegistro(Long idEscaner);

    List<RegistroLog> obtenerPorEscanerYFechaTodas(Long idEscaner, LocalDate fecha, int page, int size);

    long contarPorEscanerYFecha(Long idEscaner, LocalDate fecha);

    long contarPorEscanerYFechaTodas(Long idEscaner, LocalDate fecha);

    List<RegistroLog> buscarPorEscanerYSimbolo(Long idEscaner, String simbolo, int page, int size);

    long contarPorEscanerYSimbolo(Long idEscaner, String simbolo);

    List<RegistroLog> buscarPorEscanerYSimboloTodas(Long idEscaner, String simbolo, int page, int size);

    long contarPorEscanerYSimboloTodas(Long idEscaner, String simbolo);
}
