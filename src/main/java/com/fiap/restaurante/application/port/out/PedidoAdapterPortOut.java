package com.fiap.restaurante.application.port.out;

import java.util.List;
import java.util.UUID;

import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;

public interface PedidoAdapterPortOut {

    Pedido atualizarStatusPedido(OrderStatus status, UUID id);

    Pedido checkoutPedido(Pedido pedido);

    Pedido listarPedidoPorId(UUID id);

    List<Pedido> listarPedidos();
}
