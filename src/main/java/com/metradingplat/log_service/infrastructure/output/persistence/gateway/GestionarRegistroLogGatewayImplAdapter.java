package com.metradingplat.log_service.infrastructure.output.persistence.gateway;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metradingplat.log_service.application.output.GestionarRegistroLogGatewayIntPort;
import com.metradingplat.log_service.domain.models.RegistroLog;
import com.metradingplat.log_service.infrastructure.output.persistence.entitys.RegistroLogEntity;
import com.metradingplat.log_service.infrastructure.output.persistence.mappers.RegistroLogMapperPersistencia;
import com.metradingplat.log_service.infrastructure.output.persistence.repositories.RegistroLogRepositoryInt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GestionarRegistroLogGatewayImplAdapter implements GestionarRegistroLogGatewayIntPort {

    private final RegistroLogRepositoryInt objRegistroLogRepository;
    private final RegistroLogMapperPersistencia objMapper;

    @Override
    @Transactional
    public RegistroLog guardar(RegistroLog objRegistroLog) {
        RegistroLogEntity entity = this.objMapper.mappearDeRegistroLogAEntity(objRegistroLog);
        RegistroLogEntity saved = this.objRegistroLogRepository.save(entity);
        return this.objMapper.mappearDeEntityARegistroLog(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroLog> obtenerTodos() {
        List<RegistroLogEntity> entities = this.objRegistroLogRepository.findAll();
        return this.objMapper.mappearListaDeEntityARegistroLog(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public RegistroLog obtenerPorId(Long idRegistroLog) {
        RegistroLogEntity entity = this.objRegistroLogRepository.findById(idRegistroLog).orElse(null);
        return entity != null ? this.objMapper.mappearDeEntityARegistroLog(entity) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existePorId(Long idRegistroLog) {
        return this.objRegistroLogRepository.existsById(idRegistroLog);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroLog> obtenerPorServicioOrigen(String servicioOrigen) {
        List<RegistroLogEntity> entities = this.objRegistroLogRepository.findByServicioOrigen(servicioOrigen);
        return this.objMapper.mappearListaDeEntityARegistroLog(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroLog> obtenerPorIdEscaner(Long idEscaner) {
        List<RegistroLogEntity> entities = this.objRegistroLogRepository.findByIdEscaner(idEscaner);
        return this.objMapper.mappearListaDeEntityARegistroLog(entities);
    }
}
