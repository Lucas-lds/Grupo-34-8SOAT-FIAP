package com.fiap.restaurante.infrastructure.adapter.out;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiap.restaurante.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.PaymentStatus;
import com.fiap.restaurante.core.domain.Pedido;
import com.fiap.restaurante.core.domain.PedidoProduto;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PagamentoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.entity.PedidoProdutoEntity;
import com.fiap.restaurante.infrastructure.adapter.out.repository.PagamentoRepository;
import com.fiap.restaurante.infrastructure.adapter.out.repository.PedidoProdutoRepository;
import com.fiap.restaurante.infrastructure.adapter.out.repository.PedidoRepository;
import com.fiap.restaurante.infrastructure.adapter.out.repository.ProdutoRepository;

@Component
public class PedidoAdapterOut implements PedidoAdapterPortOut {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoProdutoRepository pedidoProdutoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public Pedido atualizarStatusPedido(OrderStatus status, Long id) {
        var pedido = pedidoRepository.findById(id);
        pedido.ifPresent(t -> {
            t.setStatus(status);
            pedidoRepository.save(t);
        });
        return mapper.map(pedido.get(), Pedido.class);
    }

    @Override
    public Pedido checkoutPedido(Pedido pedido) {
        var pedidoEntity = new PedidoEntity();
        pedidoEntity.setStatus(pedido.getStatus());
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

        var pedidoRep = mapper.map(pedidoRepository.save(pedidoEntity), Pedido.class);

        var pagamento = new PagamentoEntity();
        pagamento.setIdPedido(pedidoRep.getId());
        pagamento.setStatus(PaymentStatus.PENDING);
        pagamentoRepository.save(pagamento);

        return pedidoRep;
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
        var listaPedidos = pedidoRepository.findAllOrderedByStatus();
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
        pedidoResponse.setStatus((listaPedidoProduto.get(0).getPedido().getStatus()));
        return pedidoResponse;
    }
}
