package com.metradingplat.log_service.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.metradingplat.log_service.application.output.FormateadorResultadosIntPort;
import com.metradingplat.log_service.application.output.GestionarRegistroLogGatewayIntPort;
import com.metradingplat.log_service.application.output.PublicarLogIntPort;
import com.metradingplat.log_service.domain.usecases.GestionarRegistroLogCUAdapter;

@Configuration
public class BeanConfigurations {

    @Bean
    public GestionarRegistroLogCUAdapter gestionarRegistroLogCUIntPort(
            GestionarRegistroLogGatewayIntPort objGestionarRegistroLogGatewayIntPort,
            FormateadorResultadosIntPort objFormateadorResultadosIntPort,
            PublicarLogIntPort objPublicarLogIntPort) {
        return new GestionarRegistroLogCUAdapter(objGestionarRegistroLogGatewayIntPort,
                objFormateadorResultadosIntPort, objPublicarLogIntPort);
    }
}
