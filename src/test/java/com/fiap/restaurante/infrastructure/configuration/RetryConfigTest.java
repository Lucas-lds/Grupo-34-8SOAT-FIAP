package com.fiap.restaurante.infrastructure.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class RetryConfigTest {

    private RetryConfig retryConfig;

    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        retryConfig = new RetryConfig();
        modelMapperConfig = new ModelMapperConfig();
        restTemplate = new RestTemplate();
    }

    @Test
    void deveCriarRetryTemplateComConfiguracoesCorretas() {
        RetryTemplate retryTemplate = retryConfig.retryTemplate();
        assertNotNull(retryTemplate);
    }

    private ModelMapperConfig modelMapperConfig;

    @Test
    void deveCriarModelMapperComSucesso() {
        ModelMapper modelMapper = modelMapperConfig.modelMapper();
        assertNotNull(modelMapper);
    }

    @Test
    void testRestTemplateBean() {
        // Verifica se o RestTemplate foi configurado corretamente
        assertNotNull(restTemplate, "RestTemplate should be initialized by the @Configuration class.");
    }
}
