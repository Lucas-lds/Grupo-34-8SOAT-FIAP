package com.fiap.restaurante.application.service;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import com.fiap.restaurante.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.application.port.out.PedidoServicePortOut;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;

@Service
public class PedidoService implements PedidoServicePortOut {

    private final PedidoAdapterPortOut pedidoAdapterPortOut;

    public PedidoService(PedidoAdapterPortOut pedidoAdapterPortOut) {
        this.pedidoAdapterPortOut = pedidoAdapterPortOut;
    }

    @Override
    public Pedido atualizarStatusPedido(Integer status, UUID id) throws BadRequestException {
        return pedidoAdapterPortOut.atualizarStatusPedido(OrderStatus.fromStatusCode(status), id);

    }

    @Override
    public Pedido checkoutPedido(Pedido pedido) {
        pedido.setStatus(OrderStatus.RECEIVED);
        return pedidoAdapterPortOut.checkoutPedido(pedido);
    }

    @Override
    public Pedido listarPedidoPorId(UUID id) {
        return pedidoAdapterPortOut.listarPedidoPorId(id);
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoAdapterPortOut.listarPedidos();
    }


}
