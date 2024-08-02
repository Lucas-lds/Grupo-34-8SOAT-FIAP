package com.fiap.restaurante.core.usecase;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fiap.restaurante.application.port.out.PedidoServicePortOut;
import com.fiap.restaurante.application.port.out.usecase.PedidoUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.PedidoResponse;

@Component
public class PedidoUseCase implements PedidoUseCasePortOut{

    private final PedidoServicePortOut pedidoServicePortOut;
    private final ModelMapper mapper;

    public PedidoUseCase( PedidoServicePortOut pedidoServicePortOut, ModelMapper mapper) {
        this.pedidoServicePortOut = pedidoServicePortOut;
        this.mapper = mapper;
    }


    @Override
    public PedidoResponse atualizarStatusPedido(Integer status, Integer id) throws BadRequestException {
        return mapper.map(pedidoServicePortOut.atualizarStatusPedido(status, id), PedidoResponse.class);
    }

    @Override
    public PedidoResponse criarPedido(PedidoRequest pedido) {
        return PedidoResponse.fromDomain(pedidoServicePortOut.criarPedido(pedido.toDomain()));
    }

    @Override
    public PedidoResponse listarPedidoPorId(Integer id) {
        return mapper.map(pedidoServicePortOut.listarPedidoPorId(id), PedidoResponse.class);
    }

    @Override
    public List<PedidoResponse> listarPedidos() {
        return pedidoServicePortOut.listarPedidos().stream()
            .map(pedido -> mapper.map(pedido, PedidoResponse.class)).toList();
    }
    
}
