package com.fiap.restaurante.infrastructure.configuration;

import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.application.port.out.useCase.BuscarClienteUseCasePortOut;
import com.fiap.restaurante.application.port.out.useCase.CadastrarClienteUseCasePortOut;
import com.fiap.restaurante.core.usecase.BuscarClienteUseCase;
import com.fiap.restaurante.core.usecase.CadastrarClienteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CadastrarClienteUseCasePortOut cadastrarClienteUseCase(ClienteServicePortOut clienteService) {
        return new CadastrarClienteUseCase(clienteService);
    }

    @Bean
    public BuscarClienteUseCasePortOut buscarClienteUseCase(ClienteServicePortOut clienteService) {
        return new BuscarClienteUseCase(clienteService);
    }
}