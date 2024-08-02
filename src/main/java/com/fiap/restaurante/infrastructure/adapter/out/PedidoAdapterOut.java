package com.fiap.restaurante.infrastructure.adapter.out;

import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fiap.restaurante.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ClienteEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.PedidoProdutoRepository;
import com.fiap.restaurante.infrastructure.adapter.out.repository.PedidoRepository;

@Component
public class PedidoAdapterOut implements PedidoAdapterPortOut {

    private final PedidoRepository pedidoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;
    private final ModelMapper mapper;

    public PedidoAdapterOut( PedidoRepository pedidoRepository, PedidoProdutoRepository pedidoProdutoRepository, ModelMapper mapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoProdutoRepository = pedidoProdutoRepository;
        this.mapper = mapper;
    }

    @Override
    public Pedido atualizarStatusPedido(OrderStatus status, Long id) {
        var pedido = pedidoRepository.findById(id);
        pedido.ifPresent(t -> {
            t.setStatus_(status);
            pedidoRepository.save(t);
        });
        return mapper.map(pedido.get(), Pedido.class);
    }

    @Override
    public Pedido criarPedido(Pedido pedido) {
        var pedidoEntity = new PedidoEntity();
        pedidoEntity.setStatus_(pedido.getStatus());
        pedidoEntity.setCliente(ClienteEntity.fromDomain(new Cliente(pedido.getIdCliente())));
        var pedidoProdutos = new HashSet<PedidoProdutoEntity>();
        pedido.getListaPedidoProdutos().forEach((pedidoProduto) -> {
            var entity = new PedidoProdutoEntity();
            entity.setPedido(pedidoEntity);
            entity.setProduto(ProdutoEntity.fromDomain(pedidoProduto.getProduto()));
            entity.setQuantidade(pedidoProduto.getQuantidade());
            pedidoProdutos.add(entity);
        });
        pedidoEntity.setPedidoProdutos(pedidoProdutos);
        return mapper.map(pedidoRepository.save(pedidoEntity), Pedido.class);
    }

    @Override
    public Pedido listarPedidoPorId(Long id) {
        return mapper.map(pedidoRepository.findById(id), Pedido.class);
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll().stream()
            .map(pedido -> mapper.map(pedido, Pedido.class)).toList();
    }
    
}
