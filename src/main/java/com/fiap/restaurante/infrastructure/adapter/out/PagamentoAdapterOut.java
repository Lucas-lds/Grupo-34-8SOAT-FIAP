package com.fiap.restaurante.infrastructure.adapter.out;

import org.springframework.stereotype.Component;

import com.fiap.restaurante.application.port.out.PagamentoAdapterPortOut;

@Component
public class PagamentoAdapterOut implements PagamentoAdapterPortOut{

    @Override
    public boolean realizarPagamento() {
        return true;
    }
    
}
