package com.fiap.restaurante.application.port.out;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.core.domain.Pedido;

public interface PedidoServicePortOut {
    Pedido atualizarStatusPedido(Integer status, Integer id) throws BadRequestException;

    Pedido criarPedido(Pedido pedido);

    Pedido listarPedidoPorId(Integer id);

    List<Pedido> listarPedidos();
}
