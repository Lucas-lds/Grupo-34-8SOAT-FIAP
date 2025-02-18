package com.fiap.restaurante.infrastructure.adapter.out;

import com.fiap.restaurante.application.port.out.ClienteAdapterPortOut;
import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ClienteEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ClienteRepository;
import com.fiap.restaurante.infrastructure.exception.EmailDuplicadoException; // Adicionando a importação da exceção
import com.fiap.restaurante.infrastructure.exception.ClienteNaoEncontradoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ClienteAdapterOut implements ClienteAdapterPortOut {

    private final ClienteRepository repository;

    public ClienteAdapterOut(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cliente buscar(String cpf) {
        return repository.findFirstByCpf(cpf)
            .map(clienteEntity -> clienteEntity.toDomain()) // Converte a entidade para o domínio
            .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente com CPF " + cpf + " não encontrado"));
    }

    @Override
    public Cliente cadastrar(Cliente cliente) {
        try {
            return repository.save(ClienteEntity.fromDomain(cliente)).toDomain();
        } catch (DataIntegrityViolationException e) {
            throw new EmailDuplicadoException("O e-mail fornecido já está cadastrado.");
        }
    }
}
