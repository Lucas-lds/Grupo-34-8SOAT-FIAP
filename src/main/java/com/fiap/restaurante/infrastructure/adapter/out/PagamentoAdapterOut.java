package com.fiap.restaurante.infrastructure.adapter.out;

import com.fiap.restaurante.application.port.out.PagamentoAdapterPortOut;

public class PagamentoAdapterOut implements PagamentoAdapterPortOut{

    @Override
    public boolean realizarPagamento() {
        return true;
    }
    
}
