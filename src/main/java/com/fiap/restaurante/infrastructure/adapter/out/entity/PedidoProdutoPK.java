package com.fiap.restaurante.infrastructure.adapter.out.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class PedidoProdutoPK implements Serializable{

    private Integer idPedido;
    private Integer idProduto;

}
