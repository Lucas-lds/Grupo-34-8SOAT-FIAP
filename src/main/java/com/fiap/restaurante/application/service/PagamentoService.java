package com.fiap.restaurante.application.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fiap.restaurante.application.port.out.PagamentoAdapterPortOut;
import com.fiap.restaurante.application.port.out.PagamentoServicePortOut;

@Service
public class PagamentoService implements PagamentoServicePortOut{

    @Autowired
    private PagamentoAdapterPortOut pagamentoAdapterPortOut;

    @Override
    public boolean pagar() {
        return this.pagamentoAdapterPortOut.realizarPagamento();
    }

    @Override
    public String gerarQRCodePagamento(Double valor, String descricao) {
        return this.pagamentoAdapterPortOut.gerarQRCodePagamento(valor, descricao);
    }

    @Override
    public ResponseEntity<String> receberNotificacao(Map<String, Object> payload) {
        return this.pagamentoAdapterPortOut.receberNotificacao(payload);
    }
    
}
