package com.fiap.restaurante.infrastructure.adapter.out;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.application.port.out.PagamentoAdapterPortOut;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class PagamentoAdapterOutTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PagamentoAdapterOut pagamentoAdapterOut;

    @Value("${pagamento.service.url}")
    private String pagamentoServiceUrl;

    private Long idPedido;
    private String paymentId;

    @BeforeEach
    void setUp() {
        idPedido = 123L;
        paymentId = "987654321";
    }

    @Test
    void consultarStatusPagamentoDeveRetornarStatusComSucesso() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = new ResponseEntity<>("PENDING", HttpStatus.OK);

        when(restTemplate.exchange(eq(pagamentoServiceUrl + "/status/" + idPedido), eq(HttpMethod.GET), eq(requestEntity), eq(String.class)))
                .thenReturn(response);

        String status = pagamentoAdapterOut.consultarStatusPagamento(idPedido);

        assertNotNull(status);
        assertEquals("PENDING", status);
        verify(restTemplate, times(1)).exchange(eq(pagamentoServiceUrl + "/status/" + idPedido), eq(HttpMethod.GET), eq(requestEntity), eq(String.class));
    }

    @Test
    void consultarStatusPagamentoDeveLancarExcecaoQuandoErro() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);

        when(restTemplate.exchange(eq(pagamentoServiceUrl + "/status/" + idPedido), eq(HttpMethod.GET), eq(requestEntity), eq(String.class)))
                .thenReturn(response);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagamentoAdapterOut.consultarStatusPagamento(idPedido);
        });

        assertEquals("Erro ao consultar o status do pagamento: Erro", exception.getMessage());
        verify(restTemplate, times(1)).exchange(eq(pagamentoServiceUrl + "/status/" + idPedido), eq(HttpMethod.GET), eq(requestEntity), eq(String.class));
    }

    @Test
    void gerarQRCodePagamentoDeveRetornarQRCodeComSucesso() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject body = new JSONObject();
        body.put("valor", 100.0);
        body.put("descricao", "Pagamento teste");
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<String> response = new ResponseEntity<>("QRCodeURL", HttpStatus.OK);

        when(restTemplate.postForEntity(eq(pagamentoServiceUrl + "/gerar-qrcode"), eq(request), eq(String.class)))
                .thenReturn(response);

        String qrCode = pagamentoAdapterOut.gerarQRCodePagamento(100.0, "Pagamento teste");

        assertNotNull(qrCode);
        assertEquals("QRCodeURL", qrCode);
        verify(restTemplate, times(1)).postForEntity(eq(pagamentoServiceUrl + "/gerar-qrcode"), eq(request), eq(String.class));
    }

    @Test
    void gerarQRCodePagamentoDeveLancarExcecaoQuandoErro() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject body = new JSONObject();
        body.put("valor", 100.0);
        body.put("descricao", "Pagamento teste");
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<String> response = new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);

        when(restTemplate.postForEntity(eq(pagamentoServiceUrl + "/gerar-qrcode"), eq(request), eq(String.class)))
                .thenReturn(response);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagamentoAdapterOut.gerarQRCodePagamento(100.0, "Pagamento teste");
        });

        assertEquals("Erro ao gerar QR Code: Erro", exception.getMessage());
        verify(restTemplate, times(1)).postForEntity(eq(pagamentoServiceUrl + "/gerar-qrcode"), eq(request), eq(String.class));
    }

    @Test
    void receberNotificacaoDeveRetornarRespostaComSucesso() {
        Map<String, Object> payload = Map.of("evento", "pagamento");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = new ResponseEntity<>("Notificação recebida com sucesso", HttpStatus.OK);

        when(restTemplate.postForEntity(eq(pagamentoServiceUrl + "/webhook"), eq(requestEntity), eq(String.class)))
                .thenReturn(response);

        ResponseEntity<String> resultado = pagamentoAdapterOut.receberNotificacao(payload);

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals("Notificação recebida com sucesso", resultado.getBody());
        verify(restTemplate, times(1)).postForEntity(eq(pagamentoServiceUrl + "/webhook"), eq(requestEntity), eq(String.class));
    }

    @Test
    void receberNotificacaoDeveRetornarErroQuandoPayloadVazio() {
        ResponseEntity<String> resultado = pagamentoAdapterOut.receberNotificacao(null);

        assertNotNull(resultado);
        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("Payload vazio", resultado.getBody());
    }
}
