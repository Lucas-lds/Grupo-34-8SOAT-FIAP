package com.fiap.restaurante.infrastructure.adapter.in.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;
import com.fiap.restaurante.core.domain.PedidoProduto;
import com.fiap.restaurante.core.domain.Produto;

public record PedidoResponse(UUID id, String status, Long idCliente, List<PedidoProdutoResponse> listaPedidoProduto) {

    public static PedidoResponse fromDomain(Pedido pedido) {
        List<PedidoProdutoResponse> pedidoProdutoList = new ArrayList<>();
        if (pedido.getListaPedidoProduto() != null)
            pedidoProdutoList = pedido.getListaPedidoProduto().stream()
                    .map(pedidoProduto -> new PedidoProdutoResponse(ProdutoResponse.fromDomain(pedidoProduto.getProduto()),
                            pedidoProduto.getQuantidade())).toList();

        return new PedidoResponse(pedido.getId(), pedido.getStatus().toString(), pedido.getIdCliente(), pedidoProdutoList);
    }

    public static Pedido toDomain(PedidoResponse pedidoResponse) {
        return new Pedido(
                pedidoResponse.id(),
                OrderStatus.fromName(pedidoResponse.status()),
                pedidoResponse.idCliente(),
                pedidoResponse.listaPedidoProduto().stream()
                        .map(pedidoProduto -> new PedidoProduto(
                                new Produto(
                                        pedidoProduto.produtoResponse().idProduto(),
                                        pedidoProduto.produtoResponse().nome(),
                                        pedidoProduto.produtoResponse().categoria(),
                                        pedidoProduto.produtoResponse().preco(),
                                        pedidoProduto.produtoResponse().descricao(),
                                        pedidoProduto.produtoResponse().imagemUrl()
                                ),
                                pedidoProduto.quantidade()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
