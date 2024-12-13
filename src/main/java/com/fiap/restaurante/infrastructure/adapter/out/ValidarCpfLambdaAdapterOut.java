package com.fiap.restaurante.infrastructure.adapter.out;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.restaurante.application.port.out.ValidarCpfLambdaPortOut;
import com.fiap.restaurante.infrastructure.exception.ClienteSemPermissaoCognitoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ValidarCpfLambdaAdapterOut implements ValidarCpfLambdaPortOut {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${aws.gateway.url:}")
    private String apiGatewayUrl;

    public ValidarCpfLambdaAdapterOut(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void validarCpf(String cpf) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            String requestBody = "{\"body\": \"{\\\"cpf\\\": \\\"" + cpf + "\\\"}\"}";

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(apiGatewayUrl, HttpMethod.POST, entity,
                    String.class);

            JsonNode jsonResponse = objectMapper.readTree(response.getBody());

            if (jsonResponse.get("statusCode").asInt() == HttpStatus.NOT_FOUND.value()) {
                throw new ClienteSemPermissaoCognitoException("Cliente não tem permissão no cognito");
            }

        } catch (ClienteSemPermissaoCognitoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao conectar ao cognito: " + e);
        }
    }
}
