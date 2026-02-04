package com.metradingplat.log_service.domain.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroLog {
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
