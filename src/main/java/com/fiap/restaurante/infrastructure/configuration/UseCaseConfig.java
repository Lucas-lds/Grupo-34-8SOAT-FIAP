package com.fiap.restaurante.infrastructure.configuration;

import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.application.port.out.usecase.ClienteUseCasePortOut;
import com.fiap.restaurante.core.usecase.ClienteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public ClienteUseCasePortOut clienteUseCase(ClienteServicePortOut clienteService) {
        return new ClienteUseCase(clienteService);
    }

}