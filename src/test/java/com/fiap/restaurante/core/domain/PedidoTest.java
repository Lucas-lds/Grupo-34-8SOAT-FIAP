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

    @Test
    void testEquals() {
        UUID id = UUID.randomUUID();
        Pedido pedido1 = new Pedido(id, OrderStatus.RECEIVED, 1L, List.of(new PedidoProduto()));
        Pedido pedido2 = new Pedido(id, OrderStatus.RECEIVED, 1L, List.of(new PedidoProduto()));
        Pedido pedido3 = new Pedido(UUID.randomUUID(), OrderStatus.RECEIVED, 1L, List.of(new PedidoProduto()));

        assertEquals(pedido1, pedido2);
        assertNotEquals(pedido1, pedido3);
        assertNotEquals(pedido1, null);
        assertNotEquals(pedido1, new Object());
    }

    @Test
    void testHashCode() {
        UUID id = UUID.randomUUID();
        Pedido pedido1 = new Pedido(id, OrderStatus.RECEIVED, 1L, List.of(new PedidoProduto()));
        Pedido pedido2 = new Pedido(id, OrderStatus.RECEIVED, 1L, List.of(new PedidoProduto()));

        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }

    @Test
    void testToString() {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido(id, OrderStatus.RECEIVED, 1L, List.of(new PedidoProduto()));
        
        String result = pedido.toString();
        
        assertTrue(result.contains(id.toString()));
        assertTrue(result.contains("RECEIVED"));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("listaPedidoProduto"));
    }


}
