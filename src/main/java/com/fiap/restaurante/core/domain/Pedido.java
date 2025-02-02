package com.fiap.restaurante.core.domain;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    
    private UUID id;
    private OrderStatus status;
    private Long idCliente;

    private List<PedidoProduto> listaPedidoProduto;

    public Pedido(Long idCliente, List<PedidoProduto> list) {
        this.idCliente = idCliente;
        this.listaPedidoProduto = list;
    }
}
