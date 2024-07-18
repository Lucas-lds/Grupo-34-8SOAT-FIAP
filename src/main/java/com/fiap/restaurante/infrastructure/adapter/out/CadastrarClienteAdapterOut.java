package com.fiap.restaurante.infrastructure.adapter.out;

import com.fiap.restaurante.application.port.out.CadastrarClienteAdapterPortOut;
import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ClienteEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
public class CadastrarClienteAdapterOut implements CadastrarClienteAdapterPortOut {

    private final ClienteRepository repository;

    public CadastrarClienteAdapterOut(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cliente cadastrar(Cliente cliente) {
        return repository.save(ClienteEntity.fromDomain(cliente)).toDomain();
    }
}
