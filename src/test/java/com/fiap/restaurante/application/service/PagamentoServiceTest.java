package com.fiap.restaurante.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.application.port.out.PagamentoAdapterPortOut;
import com.mercadopago.resources.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    private PagamentoAdapterPortOut pagamentoAdapterPortOut;

    @InjectMocks
    private PagamentoService pagamentoService;

    private Long idPedido;
    private String paymentId;
    private Map<String, Object> payload;

    @BeforeEach
    void setUp() {
        idPedido = 123L;
        paymentId = "987654321";
        payload = Map.of("key", "value");
    }

    @Test
    void consultarStatusPagamentoDeveRetornarStatusComSucesso() {
        when(pagamentoAdapterPortOut.consultarStatusPagamento(anyLong())).thenReturn("PENDING");

        String status = pagamentoService.consultarStatusPagamento(idPedido);

        assertNotNull(status);
        verify(pagamentoAdapterPortOut, times(1)).consultarStatusPagamento(anyLong());
    }

    @Test
    void gerarQRCodePagamentoDeveGerarQRCodeComSucesso() {
        when(pagamentoAdapterPortOut.gerarQRCodePagamento(anyDouble(), anyString())).thenReturn("QRCodeURL");

        String qrCode = pagamentoService.gerarQRCodePagamento(100.0, "Pagamento teste");

        assertNotNull(qrCode);
        verify(pagamentoAdapterPortOut, times(1)).gerarQRCodePagamento(anyDouble(), anyString());
    }

    @Test
    void receberNotificacaoDeveReceberNotificacaoComSucesso() {
        ResponseEntity<String> response = ResponseEntity.ok("Notificação recebida com sucesso");
        when(pagamentoAdapterPortOut.receberNotificacao(any(Map.class))).thenReturn(response);

        ResponseEntity<String> resultado = pagamentoService.receberNotificacao(payload);

        assertNotNull(resultado);
        verify(pagamentoAdapterPortOut, times(1)).receberNotificacao(any(Map.class));
    }

    @Test
    void consultarPagamentoMLDeveConsultarPagamentoComSucesso() {
        Payment payment = mock(Payment.class);
        when(pagamentoAdapterPortOut.consultarPagamentoML(anyString())).thenReturn(payment);

        Payment resultado = pagamentoService.consultarPagamentoML(paymentId);

        assertNotNull(resultado);
        verify(pagamentoAdapterPortOut, times(1)).consultarPagamentoML(anyString());
    }
}
