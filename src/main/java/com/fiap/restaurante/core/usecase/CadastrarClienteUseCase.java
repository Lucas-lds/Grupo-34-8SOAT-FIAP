package com.fiap.restaurante.core.usecase;

import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.application.port.out.useCase.CadastrarClienteUseCasePortOut;
import com.fiap.restaurante.core.domain.Cliente;

public class CadastrarClienteUseCase implements CadastrarClienteUseCasePortOut {

    private final ClienteServicePortOut clienteService;

    public CadastrarClienteUseCase(ClienteServicePortOut clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public Cliente executar(Cliente cliente) {
        return clienteService.cadastrar(cliente);
    }
}
