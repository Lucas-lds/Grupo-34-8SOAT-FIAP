package com.fiap.restaurante.application.port.out;

import com.fiap.restaurante.core.domain.Cliente;

public interface ClienteServicePortOut {
    Cliente cadastrar(Cliente cliente);

    Cliente buscar(String cpf);

    void autenticarCliente(String cpf);
}
