package com.fiap.restaurante.infrastructure.configuration;

import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.application.port.out.usecase.ClienteUseCasePortOut;
import com.fiap.restaurante.core.usecase.ClienteUseCase;
import com.fiap.restaurante.application.port.out.ProdutoServicePortOut;
import com.fiap.restaurante.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.restaurante.core.usecase.ProdutoUseCase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public ClienteUseCasePortOut clienteUseCase(ClienteServicePortOut clienteService) {
        return new ClienteUseCase(clienteService);
    }
  
  @Bean
    public ProdutoUseCasePortOut produtoUseCase(ProdutoServicePortOut produtoService) {
        return new ProdutoUseCase(produtoService);
    }

}