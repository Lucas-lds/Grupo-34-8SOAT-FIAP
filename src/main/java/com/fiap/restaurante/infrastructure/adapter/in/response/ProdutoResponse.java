package com.fiap.restaurante.infrastructure.adapter.in.response;

import com.fiap.restaurante.core.domain.Produto;

public record ProdutoResponse(Long idProduto, String nome, String categoria, Double preco, String descricao, String imagemUrl) {

    public static ProdutoResponse fromDomain(Produto produto) {
        return new ProdutoResponse(produto.getIdProduto(), produto.getNome(), produto.getCategoria(), produto.getPreco(), produto.getDescricao(), produto.getImagemUrl());
    }
}
