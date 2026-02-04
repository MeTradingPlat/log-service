package com.metradingplat.log_service.infrastructure.output.formatter;

import org.springframework.stereotype.Component;

import com.metradingplat.log_service.application.output.FormateadorResultadosIntPort;
import com.metradingplat.log_service.infrastructure.output.exceptionsController.ownExceptions.EntidadNoExisteException;
import com.metradingplat.log_service.infrastructure.output.exceptionsController.ownExceptions.ReglaNegocioException;

@Component
public class FormateadorResultadosImplAdapter implements FormateadorResultadosIntPort {

    @Override
    public void errorEntidadNoExiste(String llaveMensaje, Object... args) {
        throw new EntidadNoExisteException(llaveMensaje, args);
    }

    @Override
    public void errorReglaNegocioViolada(String llaveMensaje, Object... args) {
        throw new ReglaNegocioException(llaveMensaje, args);
    }
}
