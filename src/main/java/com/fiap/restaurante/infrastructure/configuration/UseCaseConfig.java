package com.fiap.restaurante.infrastructure.configuration;

import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.application.port.out.ProdutoServicePortOut;
import com.fiap.restaurante.application.port.out.usecase.BuscarClienteUseCasePortOut;
import com.fiap.restaurante.application.port.out.usecase.CadastrarClienteUseCasePortOut;
import com.fiap.restaurante.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.restaurante.core.usecase.BuscarClienteUseCase;
import com.fiap.restaurante.core.usecase.CadastrarClienteUseCase;
import com.fiap.restaurante.core.usecase.ProdutoUseCase;

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

    @Bean
    public ProdutoUseCasePortOut produtoUseCase(ProdutoServicePortOut produtoService) {
        return new ProdutoUseCase(produtoService);
    }
}