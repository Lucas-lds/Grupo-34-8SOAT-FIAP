package com.fiap.restaurante.infrastructure.adapter.in.request;

import com.fiap.restaurante.core.domain.Produto;

public record ProdutoPedidoRequest(Long id) {

    public Produto toDomain() {
        return new Produto(id);
    }
}
