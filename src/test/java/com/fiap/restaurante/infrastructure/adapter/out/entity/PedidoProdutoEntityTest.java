package com.fiap.restaurante.infrastructure.adapter.out.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PedidoProdutoEntityTest {

    @Test
    public void testCreatePedidoProdutoEntity() {
        ProdutoEntity produto = new ProdutoEntity("Hamburguer", "Comida", 10.0, "Delicioso hamburguer", "imagemUrl");
        PedidoEntity pedido = new PedidoEntity(); // Use default constructor

        PedidoProdutoEntity pedidoProdutoEntity = new PedidoProdutoEntity(); // Use default constructor
        pedidoProdutoEntity.setPedido(pedido);
        pedidoProdutoEntity.setProduto(produto);
        pedidoProdutoEntity.setQuantidade(2);


        assertNotNull(pedidoProdutoEntity);
        assertEquals(pedido, pedidoProdutoEntity.getPedido());
        assertEquals(produto, pedidoProdutoEntity.getProduto());
        assertEquals(2, pedidoProdutoEntity.getQuantidade());
    }
}
