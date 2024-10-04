package com.fiap.restaurante.application.port.out;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.core.domain.Pedido;

public interface PedidoServicePortOut {
    Pedido atualizarStatusPedido(Integer status, Long id) throws BadRequestException;

    Pedido checkoutPedido(Pedido pedido);

    Pedido listarPedidoPorId(Long id);

    List<Pedido> listarPedidos();
}
