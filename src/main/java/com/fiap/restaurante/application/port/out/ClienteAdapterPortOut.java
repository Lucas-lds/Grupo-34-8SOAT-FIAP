package com.fiap.restaurante.application.port.out;

import com.fiap.restaurante.core.domain.Cliente;

public interface ClienteAdapterPortOut {
    Cliente buscar(String cpf);

    Cliente cadastrar(Cliente cliente);
}
