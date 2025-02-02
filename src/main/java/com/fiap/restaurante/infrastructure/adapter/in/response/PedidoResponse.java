package com.fiap.restaurante.infrastructure.adapter.in.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fiap.restaurante.core.domain.Pedido;

public record PedidoResponse(UUID id, String status, Long idCliente, List<PedidoProdutoResponse> listaPedidoProduto) {

    public static PedidoResponse fromDomain(Pedido pedido){
        List<PedidoProdutoResponse> pedidoProdutoList = new ArrayList<>();
        if(pedido.getListaPedidoProduto() != null)
            pedidoProdutoList = pedido.getListaPedidoProduto().stream()
                .map(pedidoProduto -> new PedidoProdutoResponse(ProdutoResponse.fromDomain(pedidoProduto.getProduto()),
                pedidoProduto.getQuantidade())).toList();

        return new PedidoResponse(pedido.getId(), pedido.getStatus().toString(), pedido.getIdCliente(), pedidoProdutoList);
         
    }

}