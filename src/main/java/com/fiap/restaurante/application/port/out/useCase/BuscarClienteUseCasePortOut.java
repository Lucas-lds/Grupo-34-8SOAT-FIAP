package com.fiap.restaurante.application.port.out.useCase;

import com.fiap.restaurante.core.domain.Cliente;

public interface BuscarClienteUseCasePortOut {

    Cliente executar(String cpf);
}
