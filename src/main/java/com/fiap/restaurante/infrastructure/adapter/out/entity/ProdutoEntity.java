package com.fiap.restaurante.infrastructure.adapter.out.entity;

import com.fiap.restaurante.core.domain.Produto;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_produtos")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "preco")
    private double preco;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "imagem_url")
    private String imagemUrl;

    public ProdutoEntity() {
    }

    public ProdutoEntity(String nome, String categoria, double preco, String descricao, String imagemUrl) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
    }

    public Produto toDomain() {
        return new Produto(id, nome, categoria, preco, descricao, imagemUrl);
    }

    public static ProdutoEntity fromDomain(Produto produto) {
        return new ProdutoEntity(produto.getNome(), produto.getCategoria(), produto.getPreco(), produto.getDescricao(), produto.getImagemUrl());
    }

    public void setId(Long id) {
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

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
