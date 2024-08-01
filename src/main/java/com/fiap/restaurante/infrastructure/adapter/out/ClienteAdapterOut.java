package com.fiap.restaurante.infrastructure.adapter.out;

import com.fiap.restaurante.application.port.out.ClienteAdapterPortOut;
import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ClienteEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
public class ClienteAdapterOut implements ClienteAdapterPortOut {

    private final ClienteRepository repository;

    public ClienteAdapterOut(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cliente buscar(String cpf) {
        return repository.findFirstByCpf(cpf).orElseThrow().toDomain();
    }

    @Override
    public Cliente cadastrar(Cliente cliente) {
        return repository.save(ClienteEntity.fromDomain(cliente)).toDomain();
    }
}
