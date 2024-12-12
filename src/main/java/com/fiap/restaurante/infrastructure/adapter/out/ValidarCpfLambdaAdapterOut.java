package com.fiap.restaurante.infrastructure.adapter.out;

import com.fiap.restaurante.application.port.out.ValidarCpfLambdaPortOut;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ValidarCpfLambdaAdapterOut implements ValidarCpfLambdaPortOut {

    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    @Value("${api.gateway.url:}")
    private String apiGatewayUrl;

    public ValidarCpfLambdaAdapterOut(RetryTemplate retryTemplate) {
        this.restTemplate = new RestTemplate();
        this.retryTemplate = retryTemplate;
    }

    @PostConstruct
    public void init() {
        retryTemplate.execute(context -> {
            if (apiGatewayUrl == null || apiGatewayUrl.isEmpty()) {
                throw new RuntimeException("A URL do API Gateway não está configurada.");
            }
            return null;
        });
    }

    @Override
    @Retryable(value = RuntimeException.class, maxAttempts = 5, backoff = @Backoff(delay = 2000))
    public void validarCpf(String cpf) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            String requestBody = "{\"body\": \"{\\\"cpf\\\": \\\"" + cpf + "\\\"}\"}";

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(apiGatewayUrl, HttpMethod.POST, entity,
                    String.class);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("Cliente não tem permissão no cognito");
            }

        } catch (Exception e) {
            throw new RuntimeException("Falha ao conectar ao cognito: " + e);
        }
    }
}
