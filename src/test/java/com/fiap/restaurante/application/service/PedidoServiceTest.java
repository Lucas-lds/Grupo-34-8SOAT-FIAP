package com.fiap.restaurante.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Assertions;


import com.fiap.restaurante.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;
import com.fiap.restaurante.core.domain.PedidoProduto;
import com.fiap.restaurante.core.domain.Produto;


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
        Produto produto = new Produto(1L);

                
        pedido = Pedido.builder()
                .id(pedidoId)
                .status(OrderStatus.RECEIVED)
                .idCliente(1L)
                .listaPedidoProduto(List.of(PedidoProduto.builder()
                        .produto(produto)
                        .quantidade(2)
                        .build()))
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

    @Test
    void listarPedidosDeveRetornarListaVaziaQuandoNenhumPedido() {
        when(pedidoAdapterPortOut.listarPedidos()).thenReturn(List.of());

        List<Pedido> resultado = pedidoService.listarPedidos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void listarPedidosDeveLancarExcecaoQuandoErroDeConexao() {
        when(pedidoAdapterPortOut.listarPedidos()).thenThrow(new RuntimeException("Erro de conexão com o banco de dados"));

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            pedidoService.listarPedidos();
        });

        assertEquals("Erro de conexão com o banco de dados", exception.getMessage());
    }


    // New tests for improving coverage
    @Test
    void checkoutDeveLancarExcecaoQuandoErro() {
        doThrow(new RuntimeException("Error during checkout")).when(pedidoAdapterPortOut).checkoutPedido(any(Pedido.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.checkoutPedido(pedido);
        });

        assertEquals("Error during checkout", exception.getMessage());
    }

    @Test
    void listarPedidoPorIdDeveLancarExcecaoQuandoPedidoNaoEncontrado() {
        when(pedidoAdapterPortOut.listarPedidoPorId(eq(pedidoId))).thenThrow(new RuntimeException("Pedido não encontrado"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.listarPedidoPorId(pedidoId);
        });

        assertEquals("Pedido não encontrado", exception.getMessage());
    }

}
