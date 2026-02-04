package com.metradingplat.log_service.infrastructure.input.controllerGestionarRegistroLog.DTOAnswer;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroLogDTORespuesta {
    private Long idRegistroLog;
    private String servicioOrigen;
    private String nivel;
    private String mensaje;
    private Long idEscaner;
    private String symbol;
    private String categoria;
    private LocalDateTime timestamp;
    private String metadatos;
}
