package com.fiap.restaurante.infrastructure.adapter.out.entity;

import com.fiap.restaurante.core.domain.Produto;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_produtos")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "preco")
    private double preco;

    @Column(name = "descricao")
    private String descricao;

    public ProdutoEntity() {
    }

    public ProdutoEntity(Integer id, String nome, String categoria, double preco, String descricao) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.descricao = descricao;
    }

    public Produto toDomain() {
        return new Produto(id, nome, categoria, preco, descricao);
    }

    public static ProdutoEntity fromDomain(Produto produto) {
        return new ProdutoEntity(produto.getIdProduto(), produto.getNome(), produto.getCategoria(), produto.getPreco(), produto.getDescricao());
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
