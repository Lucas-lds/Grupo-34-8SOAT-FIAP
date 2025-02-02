package com.fiap.restaurante.application.port.out.usecase;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;

import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.PedidoResponse;

public interface PedidoUseCasePortOut {
    PedidoResponse atualizarStatusPedido(Integer status, UUID id) throws BadRequestException;

    PedidoResponse checkoutPedido(PedidoRequest pedido);

    PedidoResponse listarPedidoPorId(UUID id);

    List<PedidoResponse> listarPedidos();
}
