package com.metradingplat.log_service.domain.usecases;

import java.time.LocalDateTime;
import java.util.List;

import com.metradingplat.log_service.application.input.GestionarRegistroLogCUIntPort;
import com.metradingplat.log_service.application.output.FormateadorResultadosIntPort;
import com.metradingplat.log_service.application.output.GestionarRegistroLogGatewayIntPort;
import com.metradingplat.log_service.application.output.PublicarLogIntPort;
import com.metradingplat.log_service.domain.models.RegistroLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GestionarRegistroLogCUAdapter implements GestionarRegistroLogCUIntPort {

    private final GestionarRegistroLogGatewayIntPort objGestionarRegistroLogGatewayIntPort;
    private final FormateadorResultadosIntPort objFormateadorResultadosIntPort;
    private final PublicarLogIntPort objPublicarLogIntPort;

    @Override
    public RegistroLog registrarLog(RegistroLog objRegistroLog) {
        if (objRegistroLog.getTimestamp() == null) {
            objRegistroLog.setTimestamp(LocalDateTime.now());
        }
        log.debug("Registrando log de {}: [{}] {}", objRegistroLog.getServicioOrigen(),
                objRegistroLog.getNivel(), objRegistroLog.getMensaje());

        RegistroLog guardado = this.objGestionarRegistroLogGatewayIntPort.guardar(objRegistroLog);

        // Republicar para notificaciones en tiempo real
        this.objPublicarLogIntPort.publicarLogParaNotificaciones(guardado);

        return guardado;
    }

    @Override
    public List<RegistroLog> listar() {
        return this.objGestionarRegistroLogGatewayIntPort.obtenerTodos();
    }

    @Override
    public RegistroLog obtenerPorId(Long idRegistroLog) {
        if (!this.objGestionarRegistroLogGatewayIntPort.existePorId(idRegistroLog)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("error.log.notFound", idRegistroLog);
        }
        return this.objGestionarRegistroLogGatewayIntPort.obtenerPorId(idRegistroLog);
    }

    @Override
    public List<RegistroLog> obtenerPorServicio(String servicioOrigen) {
        return this.objGestionarRegistroLogGatewayIntPort.obtenerPorServicioOrigen(servicioOrigen);
    }

    @Override
    public List<RegistroLog> obtenerPorEscaner(Long idEscaner) {
        return this.objGestionarRegistroLogGatewayIntPort.obtenerPorIdEscaner(idEscaner);
    }
}
