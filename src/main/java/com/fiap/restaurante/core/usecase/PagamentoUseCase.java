package com.fiap.restaurante.core.usecase;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fiap.restaurante.application.port.out.PagamentoServicePortOut;
import com.fiap.restaurante.application.port.out.usecase.PagamentoUseCasePortOut;
import com.mercadopago.resources.Payment;

@Component
public class PagamentoUseCase implements PagamentoUseCasePortOut{

    @Autowired
    private PagamentoServicePortOut pagamentoServicePortOut;

    @Override
    public String consultarStatusPagamento(Long idPedido) {
        return this.pagamentoServicePortOut.consultarStatusPagamento(idPedido);
    }

    @Override
    public String gerarQRCodePagamento(Double valor, String descricao) {
        return this.pagamentoServicePortOut.gerarQRCodePagamento(valor, descricao);
    }

    @Override
    public ResponseEntity<String> receberNotificacao(Map<String, Object> payload) {
        return this.pagamentoServicePortOut.receberNotificacao(payload);
    }

    @Override
    public Payment consultarPagamentoML(String paymentId) {
        return this.pagamentoServicePortOut.consultarPagamentoML(paymentId);
    }
    
}
