package com.fiap.restaurante.infrastructure.adapter.out.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pedido_produtos")
public class PedidoProdutoEntity {
    
    @EmbeddedId
    private PedidoProdutoPK id = new PedidoProdutoPK();

    @ManyToOne
    @MapsId("idPedido")
    @JoinColumn(name = ("id_pedido"))
    private PedidoEntity pedido;

    @ManyToOne
    @MapsId("idProduto")
    @JoinColumn(name = ("id_produto"))
    private ProdutoEntity produto;

    private Integer quantidade;

}
