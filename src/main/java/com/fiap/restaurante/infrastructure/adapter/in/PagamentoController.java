package com.fiap.restaurante.infrastructure.adapter.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.restaurante.application.port.out.usecase.PagamentoUseCasePortOut;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/pagamento")
public class PagamentoController{

    @Autowired
    private PagamentoUseCasePortOut pagamentoUseCasePortOut;

    @PostMapping
    public boolean pagar(@RequestBody String entity) {
        
        return this.pagamentoUseCasePortOut.pagar();
    }
    

}