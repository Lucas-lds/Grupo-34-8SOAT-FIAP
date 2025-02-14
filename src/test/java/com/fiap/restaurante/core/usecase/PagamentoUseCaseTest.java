package com.fiap.restaurante.core.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.application.port.out.PagamentoServicePortOut;
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
public class PagamentoUseCaseTest {

    @Mock
    private PagamentoServicePortOut pagamentoServicePortOut;

    @InjectMocks
    private PagamentoUseCase pagamentoUseCase;

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
        when(pagamentoServicePortOut.consultarStatusPagamento(anyLong())).thenReturn("PENDING");

        String status = pagamentoUseCase.consultarStatusPagamento(idPedido);

        assertNotNull(status);
        verify(pagamentoServicePortOut, times(1)).consultarStatusPagamento(anyLong());
    }

    @Test
    void gerarQRCodePagamentoDeveGerarQRCodeComSucesso() {
        when(pagamentoServicePortOut.gerarQRCodePagamento(anyDouble(), anyString())).thenReturn("QRCodeURL");

        String qrCode = pagamentoUseCase.gerarQRCodePagamento(100.0, "Pagamento teste");

        assertNotNull(qrCode);
        verify(pagamentoServicePortOut, times(1)).gerarQRCodePagamento(anyDouble(), anyString());
    }

    @Test
    void receberNotificacaoDeveReceberNotificacaoComSucesso() {
        ResponseEntity<String> response = ResponseEntity.ok("Notificação recebida com sucesso");
        when(pagamentoServicePortOut.receberNotificacao(any(Map.class))).thenReturn(response);

        ResponseEntity<String> resultado = pagamentoUseCase.receberNotificacao(payload);

        assertNotNull(resultado);
        verify(pagamentoServicePortOut, times(1)).receberNotificacao(any(Map.class));
    }

    @Test
    void consultarPagamentoMLDeveConsultarPagamentoComSucesso() {
        Payment payment = mock(Payment.class);

        when(pagamentoServicePortOut.consultarPagamentoML(anyString())).thenReturn(payment);

        Payment resultado = pagamentoUseCase.consultarPagamentoML(paymentId);

        assertNotNull(resultado);
        verify(pagamentoServicePortOut, times(1)).consultarPagamentoML(anyString());
    }
}
