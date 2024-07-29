package com.fiap.restaurante.application.port.out;

import java.util.List;

import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;

public interface PedidoAdapterPortOut {

    Pedido atualizarStatusPedido(OrderStatus status, Integer id);

    Pedido criarPedido(Pedido pedido);

    Pedido listarPedidoPorId(Integer id);

    List<Pedido> listarPedidos();
}
