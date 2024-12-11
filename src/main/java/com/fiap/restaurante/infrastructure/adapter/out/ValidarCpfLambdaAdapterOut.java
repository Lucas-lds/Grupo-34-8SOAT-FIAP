package com.fiap.restaurante.infrastructure.adapter.out;

import com.fiap.restaurante.application.port.out.ValidarCpfLambdaPortOut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ValidarCpfLambdaAdapterOut implements ValidarCpfLambdaPortOut {

    private final RestTemplate restTemplate;

    @Value("${api.gateway.url}")
    private String API_GATEWAY_URL;

    public ValidarCpfLambdaAdapterOut() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void validarCpf(String cpf) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            String requestBody = "{\"body\": \"{\\\"cpf\\\": \\\"" + cpf + "\\\"}\"}";

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(API_GATEWAY_URL, HttpMethod.POST, entity,
                    String.class);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("Cliente não tem permissão no cognito");
            }

        } catch (Exception e) {
            throw new RuntimeException("Falha ao conectar ao cognito: " + e);
        }
    }
}
