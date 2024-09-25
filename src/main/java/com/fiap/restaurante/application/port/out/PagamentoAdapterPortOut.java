package com.fiap.restaurante.application.port.out;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface PagamentoAdapterPortOut {
    
    boolean realizarPagamento();

    String gerarQRCodePagamento(Double valor, String descricao);

    ResponseEntity<String> receberNotificacao(Map<String, Object> payload);

}
