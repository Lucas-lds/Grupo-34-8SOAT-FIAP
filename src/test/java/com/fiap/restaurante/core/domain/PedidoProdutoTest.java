package com.fiap.restaurante.core.domain;

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PedidoProdutoTest {

    private Produto produto; // Declare produto at class level

    @BeforeEach
    void setUp() {
        produto = new Produto(1L, "Hamburguer", "Comida", 10.0, "Delicioso hamburguer", "imagemUrl");
    }

    @Test
    public void testBuilder() {
        PedidoProduto pedidoProduto = PedidoProduto.builder()
                .produto(produto)
                .quantidade(2)
                .build();

        assertNotNull(pedidoProduto);
        assertEquals(produto, pedidoProduto.getProduto());
        assertEquals(2, pedidoProduto.getQuantidade());
    }

    @Test
    public void testEqualsAndHashCode() {
        PedidoProduto pedidoProduto1 = PedidoProduto.builder()
                .produto(produto)
                .quantidade(2)
                .build();
        PedidoProduto pedidoProduto2 = PedidoProduto.builder()
                .produto(produto)
                .quantidade(2)
                .build();

        assertEquals(pedidoProduto1, pedidoProduto2);
        assertEquals(pedidoProduto1.hashCode(), pedidoProduto2.hashCode());
    }
}
