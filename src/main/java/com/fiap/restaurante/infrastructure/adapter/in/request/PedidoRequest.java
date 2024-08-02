package com.fiap.restaurante.infrastructure.adapter.in.request;

import java.util.List;

import com.fiap.restaurante.core.domain.Pedido;

public record PedidoRequest(Integer idCliente, List<PedidoProdutoRequest> listaPedidoProdutos) {
    
    public Pedido toDomain(){
        return new Pedido(idCliente, listaPedidoProdutos.stream().map(pedidoProdutos -> pedidoProdutos.toDomain()).toList());
    }

}
