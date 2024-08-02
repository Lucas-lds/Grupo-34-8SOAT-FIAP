package com.fiap.restaurante.infrastructure.adapter.in.request;

import com.fiap.restaurante.core.domain.PedidoProduto;

public record PedidoProdutoRequest(ProdutoRequest produto, Integer quantidade) {
    
    public PedidoProduto toDomain() {
        return new PedidoProduto(produto.toDomain(), quantidade);
    }

}
