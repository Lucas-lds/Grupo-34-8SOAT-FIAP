package com.fiap.restaurante.core.usecase;

import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.application.port.out.useCase.BuscarClienteUseCasePortOut;
import com.fiap.restaurante.core.domain.Cliente;

public class BuscarClienteUseCase implements BuscarClienteUseCasePortOut {

    private final ClienteServicePortOut clienteService;

    public BuscarClienteUseCase(ClienteServicePortOut clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public Cliente executar(String cpf) {
        return clienteService.buscar(cpf);
    }
}
