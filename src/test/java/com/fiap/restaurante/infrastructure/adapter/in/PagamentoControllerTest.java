package com.fiap.restaurante.infrastructure.adapter.in;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Assertions;


import com.fiap.restaurante.application.port.out.usecase.PagamentoUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.QrCodeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class PagamentoControllerTest {

    @Mock
    private PagamentoUseCasePortOut pagamentoUseCasePortOut;

    @InjectMocks
    private PagamentoController pagamentoController;

    private Long idPedido;
    private String qrCodeResponse;
    private QrCodeRequest qrCodeRequest;
    private Map<String, Object> payload;
    private ResponseEntity<String> responseEntity;

    @BeforeEach
    void setUp() {
        idPedido = 123L;
        qrCodeResponse = "QRCodeURL";
        qrCodeRequest = new QrCodeRequest(100.0, "Pagamento teste");
        payload = Map.of("key", "value");
        responseEntity = ResponseEntity.ok("Notificação recebida com sucesso");
    }

    @Test
    void consultarStatusPagamentoDeveRetornarStatusComSucesso() {
        when(pagamentoUseCasePortOut.consultarStatusPagamento(anyLong())).thenReturn("PENDING");

        String status = pagamentoController.consultarStatusPagamento(idPedido);

        assertNotNull(status);
        assertEquals("PENDING", status);
        verify(pagamentoUseCasePortOut, times(1)).consultarStatusPagamento(anyLong());
    }

    @Test
    void consultarStatusPagamentoDeveLancarExcecaoQuandoConexaoFalha() {
        when(pagamentoUseCasePortOut.consultarStatusPagamento(anyLong()))
            .thenThrow(new RuntimeException("Erro de conexão com o serviço de pagamento"));

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            pagamentoController.consultarStatusPagamento(idPedido);
        });

        assertEquals("Erro de conexão com o serviço de pagamento", exception.getMessage());
    }


    @Test
    void consultarStatusPagamentoDeveLancarExcecaoQuandoFalhar() {
        when(pagamentoUseCasePortOut.consultarStatusPagamento(anyLong()))
            .thenThrow(new RuntimeException("Erro ao consultar status"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            pagamentoController.consultarStatusPagamento(idPedido);
        });
    }


    @Test
    void gerarQrCodeDeveGerarQRCodeComSucesso() {
        when(pagamentoUseCasePortOut.gerarQRCodePagamento(anyDouble(), anyString())).thenReturn(qrCodeResponse);

        String qrCode = pagamentoController.gerarQrCode(qrCodeRequest);

        assertNotNull(qrCode);
        assertEquals(qrCodeResponse, qrCode);
        verify(pagamentoUseCasePortOut, times(1)).gerarQRCodePagamento(anyDouble(), anyString());
    }

    @Test
    void receberNotificacaoDeveReceberNotificacaoComSucesso() {
        when(pagamentoUseCasePortOut.receberNotificacao(anyMap())).thenReturn(responseEntity);

        ResponseEntity<String> response = pagamentoController.receberNotificacao(payload);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notificação recebida com sucesso", response.getBody());
        verify(pagamentoUseCasePortOut, times(1)).receberNotificacao(anyMap());
    }

    @Test
    void receberNotificacaoDeveRetornarErroQuandoPayloadInvalido() {
        when(pagamentoUseCasePortOut.receberNotificacao(anyMap()))
            .thenThrow(new IllegalArgumentException("Payload inválido"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pagamentoController.receberNotificacao(Map.of());
        });
    }

}
