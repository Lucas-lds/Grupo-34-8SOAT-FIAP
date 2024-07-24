package com.fiap.restaurante.application.port.out.usecase;

import com.fiap.restaurante.core.domain.Cliente;

public interface CadastrarClienteUseCasePortOut {

    Cliente executar(Cliente cliente);
}
