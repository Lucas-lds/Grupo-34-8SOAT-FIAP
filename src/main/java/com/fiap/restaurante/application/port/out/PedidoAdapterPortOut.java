package com.fiap.restaurante.application.port.out;

import java.util.List;

import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;

public interface PedidoAdapterPortOut {

    Pedido atualizarStatusPedido(OrderStatus status, Long id);

    Pedido checkoutPedido(Pedido pedido);

    Pedido listarPedidoPorId(Long id);

    List<Pedido> listarPedidos();
}
