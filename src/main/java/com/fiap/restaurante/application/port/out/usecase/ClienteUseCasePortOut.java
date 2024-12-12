package com.fiap.restaurante.application.port.out.usecase;

import com.fiap.restaurante.core.domain.Cliente;

public interface ClienteUseCasePortOut {

    Cliente buscarCliente(String cpf);

    Cliente cadastrarCliente(Cliente cliente);

    void validarAutenticacaoCliente(String cpf);
}
