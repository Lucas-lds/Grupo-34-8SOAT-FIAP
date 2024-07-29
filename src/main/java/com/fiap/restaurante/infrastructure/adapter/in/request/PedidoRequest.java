package com.fiap.restaurante.infrastructure.adapter.in.request;

import java.util.List;

public record PedidoRequest(List<ProdutoRequest> ListaProduto) {
    
}
