package com.metradingplat.log_service.application.output;

public interface FormateadorResultadosIntPort {
    void errorEntidadNoExiste(String llaveMensaje, Object... args);

    void errorReglaNegocioViolada(String llaveMensaje, Object... args);
}
