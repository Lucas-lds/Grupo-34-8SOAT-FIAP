package com.fiap.restaurante.application.port.out;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.core.domain.Pedido;

public interface PedidoServicePortOut {
    Pedido atualizarStatusPedido(Integer status, UUID id) throws BadRequestException;

    Pedido checkoutPedido(Pedido pedido);

    Pedido listarPedidoPorId(UUID id);

    List<Pedido> listarPedidos();
}
