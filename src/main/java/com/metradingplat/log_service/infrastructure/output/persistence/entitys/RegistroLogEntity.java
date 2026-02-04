package com.metradingplat.log_service.infrastructure.output.persistence.entitys;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "registros_log")
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(columnDefinition = "TEXT")
    private String metadatos;
}
