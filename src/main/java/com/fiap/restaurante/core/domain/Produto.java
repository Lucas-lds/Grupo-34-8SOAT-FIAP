package com.fiap.restaurante.core.domain;
public class Produto {
    private Integer id;
    private String nome;
    private String categoria;
    private double preco;
    private String descricao;

    public Produto(Integer idProduto, String nome, String categoria, double preco, String descricao) {
        this.id = idProduto;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }
}
