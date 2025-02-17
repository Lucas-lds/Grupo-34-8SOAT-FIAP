package com.fiap.restaurante.infrastructure.adapter.in.response;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class PedidoResponseTest {

    @Test
    public void testCreatePedidoResponse() {
        PedidoProdutoResponse produtoResponse = new PedidoProdutoResponse(new ProdutoResponse(1L, "Hamburguer", "Comida", 10.0, "Delicioso hamburguer", "imagemUrl"), 2);
        PedidoResponse pedidoResponse = new PedidoResponse(UUID.randomUUID(), "PENDING", 1L, List.of(produtoResponse));

        assertNotNull(pedidoResponse);
        assertEquals(produtoResponse, pedidoResponse.listaPedidoProduto().get(0));
        assertEquals("PENDING", pedidoResponse.status());
    }
}
