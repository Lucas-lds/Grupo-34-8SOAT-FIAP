package com.fiap.restaurante.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;

import com.fiap.restaurante.application.port.out.PagamentoServicePortOut;
import com.fiap.restaurante.application.port.out.usecase.PagamentoUseCasePortOut;

public class PagamentoUseCase implements PagamentoUseCasePortOut{

    @Autowired
    private PagamentoServicePortOut pagamentoUseCasePortOut;

    @Override
    public boolean pagar() {
        return this.pagamentoUseCasePortOut.pagar();
    }
    
}
