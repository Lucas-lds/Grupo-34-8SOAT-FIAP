package com.fiap.restaurante.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.restaurante.infrastructure.adapter.out.PagamentoAdapterOut;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class MercadoPagoConfig {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    @Value("${mercadopago.ngrok-url}")
    private String ngrokURL;

    @Value("${mercadopago.api-qrs}")
    private String apiQRs;

    @Bean
    public PagamentoAdapterOut pagamentoAdapterOut() {
        // Injeção do Access Token ao criar o serviço
        return new PagamentoAdapterOut(accessToken, ngrokURL, apiQRs);
    }
}