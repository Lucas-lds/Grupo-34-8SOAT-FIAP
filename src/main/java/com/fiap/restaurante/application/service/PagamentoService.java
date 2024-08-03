package com.fiap.restaurante.application.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.fiap.restaurante.application.port.out.PagamentoAdapterPortOut;
import com.fiap.restaurante.application.port.out.PagamentoServicePortOut;

public class PagamentoService implements PagamentoServicePortOut{

    @Autowired
    private PagamentoAdapterPortOut pagamentoAdapterPortOut;

    @Override
    public boolean pagar() {
        return this.pagamentoAdapterPortOut.realizarPagamento();
    }
    
}
