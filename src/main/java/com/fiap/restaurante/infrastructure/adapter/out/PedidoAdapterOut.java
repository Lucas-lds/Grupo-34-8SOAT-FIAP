package com.fiap.restaurante.infrastructure.adapter.out;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiap.restaurante.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;
import com.fiap.restaurante.core.domain.PedidoProduto;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.ProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ClienteRepository;
import com.fiap.restaurante.infrastructure.adapter.out.repository.PedidoProdutoRepository;
import com.fiap.restaurante.infrastructure.adapter.out.repository.PedidoRepository;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ProdutoRepository;

import lombok.extern.slf4j.Slf4j;

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
        var pedido = pedidoRepository.findById(id);
        if(pedido.isPresent()){
            return preencherPedido(id);
        }
        throw new RuntimeException("Pedido não encontrado");
    }

    @Override
    public List<Pedido> listarPedidos() {
        var listaPedidos = pedidoRepository.findAll();
        return listaPedidos.stream().map(pedido -> preencherPedido(pedido.getId())).toList();
    }
    

    private Pedido preencherPedido(Long id){
        var listaPedidoProduto = this.pedidoProdutoRepository.findByPedidoId(id);
        var pedidoResponse = new Pedido();
        var listaProdutos = listaPedidoProduto.stream()
            .map(pedidoProduto -> new PedidoProduto(pedidoProduto.getProduto().toDomain(), pedidoProduto.getQuantidade())).toList();
        pedidoResponse.setListaPedidoProdutos(listaProdutos);
        pedidoResponse.setId(listaPedidoProduto.get(0).getPedido().getId());
        pedidoResponse.setIdCliente(listaPedidoProduto.get(0).getPedido().getIdCliente());
        pedidoResponse.setStatus((listaPedidoProduto.get(0).getPedido().getStatus_()));
        return pedidoResponse;
    }
}
