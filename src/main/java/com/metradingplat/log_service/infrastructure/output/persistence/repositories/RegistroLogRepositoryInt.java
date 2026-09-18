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

    // Sin filtro de categoria a proposito -- a diferencia de
    // findDistinctFechasByIdEscaner/findByIdEscanerAndFecha (solo SIGNAL, para
    // la pestana "Senales"), estas dos alimentan la pestana "Registro", que
    // muestra TODAS las categorias (SCANNER, SYSTEM, FILTER, etc.), no solo
    // senales.
    @Query("SELECT DISTINCT CAST(r.timestamp AS java.time.LocalDate) FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner ORDER BY CAST(r.timestamp AS java.time.LocalDate) DESC")
    List<java.time.LocalDate> findDistinctFechasByIdEscanerTodas(@Param("idEscaner") Long idEscaner);

    @Query("SELECT r FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner AND r.timestamp >= :inicio AND r.timestamp < :fin")
    List<RegistroLogEntity> findByIdEscanerAndFechaTodas(@Param("idEscaner") Long idEscaner, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin, Pageable pageable);

    // Totales para el paginador de fecha pasada (Senales/Registro): una foto
    // fija ya no crece, asi que a diferencia de "hoy" (SSE en vivo, cargar
    // mas) tiene sentido mostrar numero de pagina y saltar directo.
    @Query("SELECT COUNT(r) FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner AND r.categoria = 'SIGNAL' AND r.timestamp >= :inicio AND r.timestamp < :fin")
    long countByIdEscanerAndFecha(@Param("idEscaner") Long idEscaner, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    @Query("SELECT COUNT(r) FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner AND r.timestamp >= :inicio AND r.timestamp < :fin")
    long countByIdEscanerAndFechaTodas(@Param("idEscaner") Long idEscaner, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    // Busqueda por simbolo en TODO el historial (sin filtro de fecha) -- a
    // diferencia de los metodos de arriba, el buscador del frontend no debe
    // limitarse a lo que ya esta cargado en pantalla.
    @Query("SELECT r FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner AND r.categoria = 'SIGNAL' AND UPPER(r.symbol) LIKE UPPER(CONCAT('%', :symbol, '%'))")
    List<RegistroLogEntity> findByIdEscanerAndSymbolSignal(@Param("idEscaner") Long idEscaner, @Param("symbol") String symbol, Pageable pageable);

    @Query("SELECT COUNT(r) FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner AND r.categoria = 'SIGNAL' AND UPPER(r.symbol) LIKE UPPER(CONCAT('%', :symbol, '%'))")
    long countByIdEscanerAndSymbolSignal(@Param("idEscaner") Long idEscaner, @Param("symbol") String symbol);

    // Igual pero sin filtro de categoria, para el buscador de la pestana
    // "Registro" (ver comentario de findByIdEscanerAndFechaTodas).
    @Query("SELECT r FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner AND UPPER(r.symbol) LIKE UPPER(CONCAT('%', :symbol, '%'))")
    List<RegistroLogEntity> findByIdEscanerAndSymbolTodas(@Param("idEscaner") Long idEscaner, @Param("symbol") String symbol, Pageable pageable);

    @Query("SELECT COUNT(r) FROM RegistroLogEntity r WHERE r.idEscaner = :idEscaner AND UPPER(r.symbol) LIKE UPPER(CONCAT('%', :symbol, '%'))")
    long countByIdEscanerAndSymbolTodas(@Param("idEscaner") Long idEscaner, @Param("symbol") String symbol);
}
