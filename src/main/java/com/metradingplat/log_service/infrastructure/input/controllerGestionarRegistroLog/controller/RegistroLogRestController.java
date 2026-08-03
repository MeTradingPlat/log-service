package com.metradingplat.log_service.infrastructure.input.controllerGestionarRegistroLog.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metradingplat.log_service.application.input.GestionarRegistroLogCUIntPort;
import com.metradingplat.log_service.domain.models.RegistroLog;
import com.metradingplat.log_service.infrastructure.input.controllerGestionarRegistroLog.DTOAnswer.RegistroLogDTORespuesta;
import com.metradingplat.log_service.infrastructure.input.controllerGestionarRegistroLog.mapper.RegistroLogMapperInfraestructuraDominio;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
@Validated
public class RegistroLogRestController {

    private final GestionarRegistroLogCUIntPort objGestionarRegistroLogCUInt;
    private final RegistroLogMapperInfraestructuraDominio objMapper;

    @GetMapping
    public ResponseEntity<List<RegistroLogDTORespuesta>> listar() {
        List<RegistroLog> logs = this.objGestionarRegistroLogCUInt.listar();
        List<RegistroLogDTORespuesta> respuesta = this.objMapper.mappearListaDeRegistroLogARespuesta(logs);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroLogDTORespuesta> obtenerPorId(
            @PathVariable("id") @NotNull @Positive Long id) {
        RegistroLog log = this.objGestionarRegistroLogCUInt.obtenerPorId(id);
        RegistroLogDTORespuesta respuesta = this.objMapper.mappearDeRegistroLogARespuesta(log);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/servicio/{servicioOrigen}")
    public ResponseEntity<List<RegistroLogDTORespuesta>> obtenerPorServicio(
            @PathVariable("servicioOrigen") String servicioOrigen) {
        List<RegistroLog> logs = this.objGestionarRegistroLogCUInt.obtenerPorServicio(servicioOrigen);
        List<RegistroLogDTORespuesta> respuesta = this.objMapper.mappearListaDeRegistroLogARespuesta(logs);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/escaner/{idEscaner}")
    public ResponseEntity<List<RegistroLogDTORespuesta>> obtenerPorEscaner(
            @PathVariable("idEscaner") @NotNull @Positive Long idEscaner,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<RegistroLog> logs;
        if (fecha != null) {
            logs = this.objGestionarRegistroLogCUInt.obtenerPorEscanerYFecha(idEscaner, fecha, page, size);
        } else {
            logs = this.objGestionarRegistroLogCUInt.obtenerPorEscaner(idEscaner, page, size);
        }
        List<RegistroLogDTORespuesta> respuesta = this.objMapper.mappearListaDeRegistroLogARespuesta(logs);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/escaner/{idEscaner}/fechas")
    public ResponseEntity<List<LocalDate>> obtenerFechasSenial(
            @PathVariable("idEscaner") @NotNull @Positive Long idEscaner) {
        List<LocalDate> fechas = this.objGestionarRegistroLogCUInt.obtenerFechasSenial(idEscaner);
        return ResponseEntity.ok(fechas);
    }

    @GetMapping("/escaner/{idEscaner}/signaled-today")
    public ResponseEntity<List<String>> obtenerSimbolosSenializadosHoy(
            @PathVariable("idEscaner") @NotNull @Positive Long idEscaner) {
        List<String> symbols = this.objGestionarRegistroLogCUInt.obtenerSimbolosSenializadosHoy(idEscaner);
        return ResponseEntity.ok(symbols);
    }

    @DeleteMapping("/escaner/{idEscaner}")
    public ResponseEntity<Void> eliminarPorEscaner(
            @PathVariable("idEscaner") @NotNull @Positive Long idEscaner) {
        this.objGestionarRegistroLogCUInt.eliminarPorEscaner(idEscaner);
        return ResponseEntity.noContent().build();
    }
}
