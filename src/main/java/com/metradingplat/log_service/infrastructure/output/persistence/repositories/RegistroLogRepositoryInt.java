package com.metradingplat.log_service.infrastructure.output.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metradingplat.log_service.infrastructure.output.persistence.entitys.RegistroLogEntity;

@Repository
public interface RegistroLogRepositoryInt extends JpaRepository<RegistroLogEntity, Long> {

    List<RegistroLogEntity> findByServicioOrigen(String servicioOrigen);

    List<RegistroLogEntity> findByIdEscaner(Long idEscaner);
}
