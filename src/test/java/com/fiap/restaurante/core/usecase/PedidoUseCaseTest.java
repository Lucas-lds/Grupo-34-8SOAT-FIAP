package com.fiap.restaurante.core.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.application.port.out.PedidoServicePortOut;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;
import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.PedidoResponse;
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
public class PedidoUseCaseTest {

    @Mock
    private PedidoServicePortOut pedidoServicePortOut;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    private Pedido pedido;
    private PedidoRequest pedidoRequest;
    private PedidoResponse pedidoResponse;
    private UUID pedidoId;

    @BeforeEach
    void setUp() {
        pedidoId = UUID.randomUUID();
        pedido = Pedido.builder()
                .id(pedidoId)
                .status(OrderStatus.RECEIVED) // Garantir que o status não seja null
                .idCliente(1L)
                .listaPedidoProduto(List.of())
                .build();
        pedidoRequest = new PedidoRequest(1L, List.of());
        pedidoResponse = PedidoResponse.fromDomain(pedido);
    }

    @Test
    void atualizarStatusPedidoDeveAtualizarStatusComSucesso() throws BadRequestException {
        when(pedidoServicePortOut.atualizarStatusPedido(anyInt(), eq(pedidoId))).thenReturn(pedido);

        PedidoResponse response = pedidoUseCase.atualizarStatusPedido(1, pedidoId);

        assertNotNull(response);
        assertEquals(pedidoId, response.id());
        verify(pedidoServicePortOut, times(1)).atualizarStatusPedido(anyInt(), eq(pedidoId));
    }

    @Test
    void atualizarStatusPedidoDeveLancarExcecaoQuandoErro() throws BadRequestException {
        when(pedidoServicePortOut.atualizarStatusPedido(anyInt(), eq(pedidoId))).thenThrow(new BadRequestException("Status inválido"));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            pedidoUseCase.atualizarStatusPedido(99, pedidoId);
        });

        assertEquals("Status inválido", exception.getMessage());
    }

    @Test
    void checkoutPedidoDeveRealizarCheckoutComSucesso() {
        when(pedidoServicePortOut.checkoutPedido(any(Pedido.class))).thenReturn(pedido);

        PedidoResponse response = pedidoUseCase.checkoutPedido(pedidoRequest);

        assertNotNull(response);
        assertEquals(pedidoId, response.id());
        verify(pedidoServicePortOut, times(1)).checkoutPedido(any(Pedido.class));
    }

    @Test
    void listarPedidoPorIdDeveRetornarPedidoComSucesso() {
        when(pedidoServicePortOut.listarPedidoPorId(eq(pedidoId))).thenReturn(pedido);

        PedidoResponse response = pedidoUseCase.listarPedidoPorId(pedidoId);

        assertNotNull(response);
        assertEquals(pedidoId, response.id());
        verify(pedidoServicePortOut, times(1)).listarPedidoPorId(eq(pedidoId));
    }

    @Test
    void listarPedidosDeveRetornarListaDePedidosComSucesso() {
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoServicePortOut.listarPedidos()).thenReturn(pedidos);

        List<PedidoResponse> response = pedidoUseCase.listarPedidos();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(pedidoServicePortOut, times(1)).listarPedidos();
    }
}
