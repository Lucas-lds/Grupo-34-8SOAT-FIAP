package com.fiap.restaurante.infrastructure.adapter.in.response;

import java.util.List;

import com.fiap.restaurante.core.domain.Pedido;

public record PedidoResponse(Integer id, String status, Integer idCliente, List<PedidoProdutoResponse> ListaProduto) {

    public static PedidoResponse fromDomain(Pedido pedido){
        return new PedidoResponse(pedido.getId(), pedido.getStatus().toString(), pedido.getIdCliente(),
         pedido.getListaPedidoProdutos().stream()
         .map(pedidoProduto -> new PedidoProdutoResponse(ProdutoResponse.fromDomain(pedidoProduto.getProduto()),
          pedidoProduto.getQuantidade())).toList());
    }

}