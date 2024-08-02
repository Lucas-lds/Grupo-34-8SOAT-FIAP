package com.fiap.restaurante.core.domain;

public class Cliente {
    private Integer idCliente;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;

    public Cliente(Integer idCliente, String cpf, String nome, String email, String telefone) {
        this.idCliente = idCliente;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Cliente(Integer idCliente){
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}
