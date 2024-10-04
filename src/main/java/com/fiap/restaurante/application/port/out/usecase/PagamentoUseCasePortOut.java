package com.fiap.restaurante.application.port.out.usecase;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface PagamentoUseCasePortOut {
    String consultarStatusPagamento(Long idPedido);

    String gerarQRCodePagamento(Double valor, String descricao);

    ResponseEntity<String> receberNotificacao(Map<String, Object> payload);
}
