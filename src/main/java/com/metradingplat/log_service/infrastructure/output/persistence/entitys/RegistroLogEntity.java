package com.metradingplat.log_service.infrastructure.output.persistence.entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Sin estos indices, buscar 50 filas por escaner (Señales/Registro/busqueda
// por simbolo) hacia un Seq Scan de la tabla ENTERA seguido de un sort --
// confirmado en vivo el 2026-09-22 contra 88 mil filas: 596ms para 50 filas,
// que solo empeora con el tiempo. Con estos dos, la misma consulta baja a
// <1ms (Index Scan Backward, sin sort, ORDER BY timestamp DESC resuelto por
// el propio indice). ddl-auto=update los crea solos en el proximo deploy;
// en produccion ya se aplicaron a mano con CREATE INDEX CONCURRENTLY para no
// esperar al deploy ni bloquear escrituras mientras tanto.
@Entity
@Table(name = "registros_log", indexes = {
        @Index(name = "idx_registros_log_escaner_categoria_timestamp", columnList = "id_escaner, categoria, timestamp"),
        @Index(name = "idx_registros_log_escaner_timestamp", columnList = "id_escaner, timestamp")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_log")
    private Long idRegistroLog;

    @Column(name = "servicio_origen", nullable = false, length = 100)
    private String servicioOrigen;

    @Column(nullable = false, length = 10)
    private String nivel;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "id_escaner")
    private Long idEscaner;

    @Column(length = 20)
    private String symbol;

    @Column(length = 30)
    private String categoria;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(columnDefinition = "TEXT")
    private String metadatos;
}
