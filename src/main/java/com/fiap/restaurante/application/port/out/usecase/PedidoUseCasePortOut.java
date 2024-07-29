package com.fiap.restaurante.application.port.out.usecase;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.PedidoResponse;

public interface PedidoUseCasePortOut {
    PedidoResponse atualizarStatusPedido(Integer status, Integer id) throws BadRequestException;

    PedidoResponse criarPedido(PedidoRequest pedido);

    PedidoResponse listarPedidoPorId(Integer id);

    List<PedidoResponse> listarPedidos();
}
