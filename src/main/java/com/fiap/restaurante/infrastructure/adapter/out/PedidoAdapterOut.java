package com.fiap.restaurante.infrastructure.adapter.out;

import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiap.restaurante.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ClienteEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoProdutoPK;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ClienteRepository;
import com.fiap.restaurante.infrastructure.adapter.out.repository.PedidoProdutoRepository;
import com.fiap.restaurante.infrastructure.adapter.out.repository.PedidoRepository;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ProdutoRepository;

@Component
public class PedidoAdapterOut implements PedidoAdapterPortOut {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoProdutoRepository pedidoProdutoRepository;
    @Autowired
    private ModelMapper mapper;


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
        pedidoEntity.setIdCliente(pedido.getIdCliente());
        var pedidoSaved = this.pedidoRepository.save(pedidoEntity);
        pedido.getListaPedidoProdutos().forEach((pedidoProduto) -> {
            var entity = new PedidoProdutoEntity();
            entity.setPedido(pedidoSaved);
            var produto = this.produtoRepository.findById(pedidoProduto.getProduto().getIdProduto());
            entity.setProduto(produto.get());
            entity.setQuantidade(pedidoProduto.getQuantidade());
            this.pedidoProdutoRepository.save(entity);
        });

        return mapper.map(pedidoRepository.save(pedidoEntity), Pedido.class);
    }

    @Override
    public Pedido listarPedidoPorId(Long id) {
        if(pedidoRepository.findById(id).isPresent()){
            var pedidoProduto = this.pedidoProdutoRepository.findById(id);
        }
        return new Pedido();
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll().stream()
            .map(pedido -> mapper.map(pedido, Pedido.class)).toList();
    }
    
}
