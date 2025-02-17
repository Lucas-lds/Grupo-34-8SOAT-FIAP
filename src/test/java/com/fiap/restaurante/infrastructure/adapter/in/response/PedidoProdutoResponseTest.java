package com.fiap.restaurante.infrastructure.adapter.in.response;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PedidoProdutoResponseTest {

    @Test
    public void testCreatePedidoProdutoResponse() {
        ProdutoResponse produtoResponse = new ProdutoResponse(1L, "Hamburguer", "Comida", 10.0, "Delicioso hamburguer", "imagemUrl");
        PedidoProdutoResponse pedidoProdutoResponse = new PedidoProdutoResponse(produtoResponse, 2);

        assertNotNull(pedidoProdutoResponse);
        assertEquals(produtoResponse, pedidoProdutoResponse.produtoResponse());
        assertEquals(2, pedidoProdutoResponse.quantidade());
    }
}
