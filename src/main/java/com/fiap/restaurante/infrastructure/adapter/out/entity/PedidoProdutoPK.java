package com.fiap.restaurante.infrastructure.adapter.out.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PedidoProdutoPK implements Serializable{

    @Column(name = "id_pedido")
    private Long idPedido;
    @Column(name = "id_produto")
    private Long idProduto;

}
