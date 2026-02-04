package com.metradingplat.log_service.infrastructure.output.kafka.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroLogNotificacionDTO {
    private Long idRegistroLog;
    private String servicioOrigen;
    private String nivel;
    private String mensaje;
    private Long idEscaner;
    private String symbol;
    private String categoria;
    private LocalDateTime timestamp;
}
