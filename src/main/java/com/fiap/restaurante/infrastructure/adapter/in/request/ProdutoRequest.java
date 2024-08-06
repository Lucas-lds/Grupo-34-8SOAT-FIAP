package com.fiap.restaurante.infrastructure.adapter.in.request;

import com.fiap.restaurante.core.domain.Produto;

public record ProdutoRequest(String nome, String categoria, Double preco, String descricao, String imagemUrl) {
    
    public Produto toDomain() {
        return new Produto(null, nome, categoria, preco, descricao, imagemUrl);
    }
}
