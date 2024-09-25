package com.fiap.restaurante.application.service;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

public class MercadoPagoQrCodeService {
    private final String accessToken;

    public MercadoPagoQrCodeService(String accessToken) {
        this.accessToken = accessToken;
    }

    public String gerarQRCodePagamento(Double valor, String descricao) {
        RestTemplate restTemplate = new RestTemplate();

        // Configura o cabeçalho da requisição
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        // Configura o corpo da requisição
        JSONObject body = new JSONObject();
        body.put("external_reference", "123456");
        body.put("notification_url", "https://09e4-2804-7f0-9fc0-f09-1d97-7d4b-cb66-afe1.ngrok-free.app/api/v1/pagamentos/webhook");
        body.put("title", descricao);
        body.put("description", descricao);
        body.put("total_amount", valor);
        body.put("items", new JSONObject[]{
            new JSONObject()
                .put("title", descricao)
                .put("quantity", 1)
                .put("unit_price", valor)
                .put("unit_measure", "unit")
                .put("total_amount", valor)
        });

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        // Enviar a requisição POST
        ResponseEntity<String> response = restTemplate.postForEntity(
            "https://api.mercadopago.com/instore/orders/qr/seller/collectors/1988860756/pos/SUC001POS001/qrs",
            request,
            String.class
        );

        System.err.println(response.getStatusCode());

        if (response.getStatusCode() == HttpStatus.CREATED) {
            // Retornar o URL do QR Code
            JSONObject jsonResponse = new JSONObject(response.getBody());
            return jsonResponse.getString("qr_data");
        } else {
            throw new RuntimeException("Erro ao gerar QR Code: " + response.getBody());
        }
    }
}
