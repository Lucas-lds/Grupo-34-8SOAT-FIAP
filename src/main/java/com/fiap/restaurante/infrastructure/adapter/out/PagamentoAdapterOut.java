package com.fiap.restaurante.infrastructure.adapter.out;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fiap.restaurante.application.port.out.PagamentoAdapterPortOut;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;

import org.springframework.http.*;

@Component
public class PagamentoAdapterOut implements PagamentoAdapterPortOut {

    private final RestTemplate restTemplate;

    @Value("${pagamento.service.url}")
    private String pagamentoServiceUrl;

    public PagamentoAdapterOut(RestTemplate restTemplate) throws MPConfException {
        this.restTemplate = restTemplate;
    }

    @Override
    public String consultarStatusPagamento(Long idPedido) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(pagamentoServiceUrl + "/status/" + idPedido,
                HttpMethod.GET, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Erro ao consultar o status do pagamento: " + response.getBody());
        }
    }

    @Override
    public Payment consultarPagamentoML(String paymentId) {
        try {
            Payment payment = Payment.findById(paymentId);
            payment.getStatus();

            if (payment != null) {
                return payment;
            }
        } catch (MPException e) {
            System.err.println("Erro ao consultar o pagamento: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String gerarQRCodePagamento(Double valor, String descricao) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("valor", valor);
        body.put("descricao", descricao);

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(pagamentoServiceUrl + "/gerar-qrcode", request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Erro ao gerar QR Code: " + response.getBody());
        }
    }

    @Override
    public ResponseEntity<String> receberNotificacao(Map<String, Object> payload) {
        if (payload == null) {
            return new ResponseEntity<>("Payload vazio", HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(pagamentoServiceUrl + "/webhook", requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Erro ao receber notificação: " + response.getBody(), HttpStatus.BAD_REQUEST);
        }
    }
}
