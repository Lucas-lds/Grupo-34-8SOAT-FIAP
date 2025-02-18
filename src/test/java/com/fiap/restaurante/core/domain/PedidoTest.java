package com.fiap.restaurante.core.domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void testBuilder() {
        UUID id = UUID.randomUUID();
        Long idCliente = 1L;
        List<PedidoProduto> produtos = List.of(new PedidoProduto());

        Pedido pedido = Pedido.builder()
                .id(id)
                .idCliente(idCliente)
                .listaPedidoProduto(produtos)
                .build();

        assertNotNull(pedido);
        assertEquals(id, pedido.getId());
        assertEquals(idCliente, pedido.getIdCliente());
        assertEquals(produtos, pedido.getListaPedidoProduto());
    }

    @Test
    void testGettersAndSetters() {
        Pedido pedido = new Pedido();
        UUID id = UUID.randomUUID();
        Long idCliente = 1L;
        List<PedidoProduto> produtos = List.of(new PedidoProduto());

        pedido.setId(id);
        pedido.setIdCliente(idCliente);
        pedido.setListaPedidoProduto(produtos);

        assertEquals(id, pedido.getId());
        assertEquals(idCliente, pedido.getIdCliente());
        assertEquals(produtos, pedido.getListaPedidoProduto());
    }

    @Test
    void testNoArgsConstructor() {
        Pedido pedido = new Pedido();
        assertNotNull(pedido);
        assertNull(pedido.getId());
        assertNull(pedido.getIdCliente());
        assertNull(pedido.getListaPedidoProduto());
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        Long idCliente = 1L;
        List<PedidoProduto> produtos = List.of(new PedidoProduto());

        Pedido pedido = new Pedido(id, OrderStatus.RECEIVED, idCliente, produtos);


        assertNotNull(pedido);
        assertEquals(id, pedido.getId());
        assertEquals(OrderStatus.RECEIVED, pedido.getStatus());

        assertEquals(idCliente, pedido.getIdCliente());
        assertEquals(produtos, pedido.getListaPedidoProduto());
    }
}
