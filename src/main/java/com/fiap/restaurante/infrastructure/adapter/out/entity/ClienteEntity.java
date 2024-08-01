package com.fiap.restaurante.infrastructure.adapter.out.entity;

import com.fiap.restaurante.core.domain.Cliente;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_clientes")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer id;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    public ClienteEntity() {
    }

    public ClienteEntity(Integer id, String cpf, String nome, String email) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    public Cliente toDomain() {
        return new Cliente(id, cpf, nome, email);
    }

    public static ClienteEntity fromDomain(Cliente cliente) {
        return new ClienteEntity(cliente.getIdCliente(), cliente.getCpf(), cliente.getNome(), cliente.getEmail());
    }
}