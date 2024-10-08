package com.fiap.restaurante.application.port.out;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mercadopago.resources.Payment;

public interface PagamentoServicePortOut {
    String consultarStatusPagamento(Long idPedido);

    String gerarQRCodePagamento(Double valor, String descricao);

    ResponseEntity<String> receberNotificacao(Map<String, Object> payload);

    Payment consultarPagamentoML(String paymentId);
}
