package com.fiap.restaurante.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoAdapterPortOut pedidoAdapterPortOut;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private UUID pedidoId;

    @BeforeEach
    void setUp() {
        pedidoId = UUID.randomUUID();
        pedido = Pedido.builder()
                .id(pedidoId)
                .status(OrderStatus.RECEIVED)
                .idCliente(1L)
                .listaPedidoProduto(List.of())
                .build();
    }

    @Test
    void atualizarStatusPedidoDeveAtualizarStatusComSucesso() throws BadRequestException {
        when(pedidoAdapterPortOut.atualizarStatusPedido(any(OrderStatus.class), eq(pedidoId))).thenReturn(pedido);

        Pedido resultado = pedidoService.atualizarStatusPedido(1, pedidoId);

        assertNotNull(resultado);
        assertEquals(OrderStatus.RECEIVED, resultado.getStatus());
        verify(pedidoAdapterPortOut, times(1)).atualizarStatusPedido(any(OrderStatus.class), eq(pedidoId));
    }

    @Test
    void atualizarStatusPedidoDeveLancarExcecaoQuandoErro() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.atualizarStatusPedido(99, pedidoId);
        });

        assertEquals("Invalid status code: 99", exception.getMessage());
//        verify(pedidoAdapterPortOut, times(1)).atualizarStatusPedido(any(), any());
    }

    @Test
    void checkoutPedidoDeveRealizarCheckoutComSucesso() {
        when(pedidoAdapterPortOut.checkoutPedido(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.checkoutPedido(pedido);

        assertNotNull(resultado);
        assertEquals(OrderStatus.RECEIVED, resultado.getStatus());
        verify(pedidoAdapterPortOut, times(1)).checkoutPedido(any(Pedido.class));
    }

    @Test
    void listarPedidoPorIdDeveRetornarPedidoComSucesso() {
        when(pedidoAdapterPortOut.listarPedidoPorId(eq(pedidoId))).thenReturn(pedido);

        Pedido resultado = pedidoService.listarPedidoPorId(pedidoId);

        assertNotNull(resultado);
        assertEquals(pedidoId, resultado.getId());
        verify(pedidoAdapterPortOut, times(1)).listarPedidoPorId(eq(pedidoId));
    }

    @Test
    void listarPedidosDeveRetornarListaDePedidosComSucesso() {
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoAdapterPortOut.listarPedidos()).thenReturn(pedidos);

        List<Pedido> resultado = pedidoService.listarPedidos();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(pedidoAdapterPortOut, times(1)).listarPedidos();
    }
}
