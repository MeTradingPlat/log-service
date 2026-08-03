package com.metradingplat.log_service.infrastructure.output.persistence.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.metradingplat.log_service.infrastructure.output.persistence.entitys.RegistroLogEntity;

@Repository
public interface RegistroLogRepositoryInt extends JpaRepository<RegistroLogEntity, Long> {

    List<RegistroLogEntity> findByServicioOrigen(String servicioOrigen);

    List<RegistroLogEntity> findByIdEscaner(Long idEscaner);

    List<RegistroLogEntity> findByIdEscaner(Long idEscaner, Pageable pageable);

    void deleteByIdEscaner(Long idEscaner);

    @Query("SELECT DISTINCT CAST(r.timestamp AS java.time.LocalDate) FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner AND r.categoria = 'SIGNAL' ORDER BY CAST(r.timestamp AS java.time.LocalDate) DESC")
    List<java.time.LocalDate> findDistinctFechasByIdEscaner(@Param("idEscaner") Long idEscaner);

    @Query("SELECT r FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner AND r.categoria = 'SIGNAL' AND r.timestamp >= :inicio AND r.timestamp < :fin")
    List<RegistroLogEntity> findByIdEscanerAndFecha(@Param("idEscaner") Long idEscaner, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin, Pageable pageable);

    @Query("SELECT DISTINCT r.symbol FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner AND r.categoria = 'SIGNAL' AND r.timestamp >= :inicio AND r.timestamp < :fin")
    List<String> findDistinctSymbolsByIdEscanerAndFecha(@Param("idEscaner") Long idEscaner, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
