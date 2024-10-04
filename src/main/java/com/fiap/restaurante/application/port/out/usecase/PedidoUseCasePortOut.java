package com.fiap.restaurante.application.port.out.usecase;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.PedidoResponse;

public interface PedidoUseCasePortOut {
    PedidoResponse atualizarStatusPedido(Integer status, Long id) throws BadRequestException;

    PedidoResponse checkoutPedido(PedidoRequest pedido);

    PedidoResponse listarPedidoPorId(Long id);

    List<PedidoResponse> listarPedidos();
}
