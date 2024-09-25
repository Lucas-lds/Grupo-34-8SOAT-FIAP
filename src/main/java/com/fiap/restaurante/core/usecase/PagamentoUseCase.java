package com.fiap.restaurante.core.usecase;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fiap.restaurante.application.port.out.PagamentoServicePortOut;
import com.fiap.restaurante.application.port.out.usecase.PagamentoUseCasePortOut;

@Component
public class PagamentoUseCase implements PagamentoUseCasePortOut{

    @Autowired
    private PagamentoServicePortOut pagamentoUseCasePortOut;

    @Override
    public boolean pagar() {
        return this.pagamentoUseCasePortOut.pagar();
    }

    @Override
    public String gerarQRCodePagamento(Double valor, String descricao) {
        return this.pagamentoUseCasePortOut.gerarQRCodePagamento(valor, descricao);
    }

    @Override
    public ResponseEntity<String> receberNotificacao(Map<String, Object> payload) {
        return this.pagamentoUseCasePortOut.receberNotificacao(payload);
    }
    
}
