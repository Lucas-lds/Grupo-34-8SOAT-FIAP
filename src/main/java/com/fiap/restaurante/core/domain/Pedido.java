package com.fiap.restaurante.core.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    
    private Integer id;
    private OrderStatus status;
    private Integer idCliente;
    private List<PedidoProduto> listaPedidoProdutos;

    public Pedido(Integer idCliente, List<PedidoProduto> list) {
        this.idCliente = idCliente;
        this.listaPedidoProdutos = list;
    }
}
