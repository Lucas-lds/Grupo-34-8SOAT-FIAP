package com.fiap.restaurante.application.service;

import com.fiap.restaurante.application.port.out.ClienteAdapterPortOut;
import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.core.domain.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements ClienteServicePortOut {

    private final ClienteAdapterPortOut clienteAdapterPortOut;

    public ClienteService(ClienteAdapterPortOut clienteAdapterPortOut) {
        this.clienteAdapterPortOut = clienteAdapterPortOut;
    }

    @Override
    public Cliente cadastrar(Cliente cliente) {
        return clienteAdapterPortOut.cadastrar(cliente);
    }

    @Override
    public Cliente buscar(String cpf) {
        return clienteAdapterPortOut.buscar(cpf);
    }

}
