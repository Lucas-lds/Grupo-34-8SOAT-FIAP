package com.fiap.restaurante.infrastructure.adapter.in.response;

import com.fiap.restaurante.core.domain.Produto;

public record ProdutoResponse(Integer id, String nome, String categoria, Double preco, String descricao) {

    public static ProdutoResponse fromDomain(Produto produto) {
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getCategoria(), produto.getPreco(), produto.getDescricao());
    }
}
