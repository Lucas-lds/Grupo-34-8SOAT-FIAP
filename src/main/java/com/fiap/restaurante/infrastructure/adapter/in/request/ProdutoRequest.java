package com.fiap.restaurante.infrastructure.adapter.in.request;

import com.fiap.restaurante.core.domain.Produto;

public record ProdutoRequest(Long id, String nome, String categoria, Double preco, String descricao) {
    
    public Produto toDomain() {
        return new Produto(id, nome, categoria, preco, descricao);
    }
}
