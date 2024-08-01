package com.fiap.restaurante.infrastructure.adapter.out;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fiap.restaurante.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.PedidoRepository;

@Component
public class PedidoAdapterOut implements PedidoAdapterPortOut {

    private final PedidoRepository pedidoRepository;
    private final ModelMapper mapper;

    public PedidoAdapterOut( PedidoRepository pedidoRepository, ModelMapper mapper) {
        this.pedidoRepository = pedidoRepository;
        this.mapper = mapper;
    }

    @Override
    public Pedido atualizarStatusPedido(OrderStatus status, Integer id) {
        var pedido = pedidoRepository.findById(id);
        pedido.ifPresent(t -> {
            t.setStatus_(status);
            pedidoRepository.save(t);
        });
        return mapper.map(pedido.get(), Pedido.class);
    }

    @Override
    public Pedido criarPedido(Pedido pedido) {
        return mapper.map(pedidoRepository.save(mapper.map(pedido, PedidoEntity.class)), Pedido.class);
    }

    @Override
    public Pedido listarPedidoPorId(Integer id) {
        return mapper.map(pedidoRepository.findById(id), Pedido.class);
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll().stream()
            .map(pedido -> mapper.map(pedido, Pedido.class)).toList();
    }
    
}
