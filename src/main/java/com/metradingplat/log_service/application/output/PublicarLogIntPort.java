package com.metradingplat.log_service.application.output;

import com.metradingplat.log_service.domain.models.RegistroLog;

public interface PublicarLogIntPort {
    void publicarLogParaNotificaciones(RegistroLog objRegistroLog);
}
