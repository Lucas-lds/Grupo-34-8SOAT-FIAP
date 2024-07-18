package com.fiap.restaurante.application.service;

import com.fiap.restaurante.application.port.out.BuscarClienteAdapterPortOut;
import com.fiap.restaurante.application.port.out.CadastrarClienteAdapterPortOut;
import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.core.domain.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteService implements ClienteServicePortOut {

    private final CadastrarClienteAdapterPortOut cadastrarClienteAdapterPortOut;
    private final BuscarClienteAdapterPortOut buscarClienteAdapterOut;

    public ClienteService(CadastrarClienteAdapterPortOut cadastrarClienteAdapterPortOut, BuscarClienteAdapterPortOut buscarClienteAdapterOut) {
        this.cadastrarClienteAdapterPortOut = cadastrarClienteAdapterPortOut;
        this.buscarClienteAdapterOut = buscarClienteAdapterOut;
    }

    @Override
    public Cliente cadastrar(Cliente cliente) {
        return cadastrarClienteAdapterPortOut.cadastrar(cliente);
    }

    public Cliente buscar(String cpf) {
        return buscarClienteAdapterOut.buscar(cpf);
    }

}
