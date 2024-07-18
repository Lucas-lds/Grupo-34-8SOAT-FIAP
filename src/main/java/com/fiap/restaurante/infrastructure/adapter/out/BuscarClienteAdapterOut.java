package com.fiap.restaurante.infrastructure.adapter.out;

import com.fiap.restaurante.application.port.out.BuscarClienteAdapterPortOut;
import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarClienteAdapterOut implements BuscarClienteAdapterPortOut {

    private final ClienteRepository repository;

    public BuscarClienteAdapterOut(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cliente buscar(String cpf) {
        return repository.findFirstByCpf(cpf).orElseThrow().toDomain();
    }
}
