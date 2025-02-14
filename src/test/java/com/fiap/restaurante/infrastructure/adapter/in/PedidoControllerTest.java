package com.fiap.restaurante.infrastructure.adapter.in;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.application.port.out.usecase.PedidoUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.request.StatusRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.PedidoResponse;
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
public class PedidoControllerTest {

    @Mock
    private PedidoUseCasePortOut pedidoUseCasePortOut;

    @InjectMocks
    private PedidoController pedidoController;

    private Pedido pedido;
    private PedidoRequest pedidoRequest;
    private PedidoResponse pedidoResponse;
    private StatusRequest statusRequest;
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
        pedidoRequest = new PedidoRequest(1L, List.of());
        statusRequest = new StatusRequest(1); // Example status
        pedidoResponse = PedidoResponse.fromDomain(pedido);
    }

    @Test
    void atualizarStatusPedidoDeveAtualizarStatusComSucesso() throws BadRequestException {
        when(pedidoUseCasePortOut.atualizarStatusPedido(anyInt(), eq(pedidoId))).thenReturn(pedidoResponse);

        PedidoResponse response = pedidoController.atualizarStatusPedido(pedidoId, statusRequest);

        assertNotNull(response);
        assertEquals(pedidoId, response.id());
        verify(pedidoUseCasePortOut, times(1)).atualizarStatusPedido(anyInt(), eq(pedidoId));
    }

    @Test
    void atualizarStatusPedidoDeveLancarExcecaoQuandoErro() throws BadRequestException {
        when(pedidoUseCasePortOut.atualizarStatusPedido(anyInt(), eq(pedidoId))).thenThrow(new BadRequestException("Status inválido"));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            pedidoController.atualizarStatusPedido(pedidoId, statusRequest);
        });

        assertEquals("Status inválido", exception.getMessage());
    }

    @Test
    void checkoutPedidoDeveRealizarCheckoutComSucesso() {
        when(pedidoUseCasePortOut.checkoutPedido(any(PedidoRequest.class))).thenReturn(pedidoResponse);

        PedidoResponse response = pedidoController.checkoutPedido(pedidoRequest);

        assertNotNull(response);
        assertEquals(pedidoId, response.id());
        verify(pedidoUseCasePortOut, times(1)).checkoutPedido(any(PedidoRequest.class));
    }

    @Test
    void getPedidoByIdDeveRetornarPedidoComSucesso() {
        when(pedidoUseCasePortOut.listarPedidoPorId(eq(pedidoId))).thenReturn(pedidoResponse);

        PedidoResponse response = pedidoController.getPedidoById(pedidoId);

        assertNotNull(response);
        assertEquals(pedidoId, response.id());
        verify(pedidoUseCasePortOut, times(1)).listarPedidoPorId(eq(pedidoId));
    }

    @Test
    void listarPedidosDeveRetornarListaDePedidosComSucesso() {
        List<PedidoResponse> pedidosResponse = List.of(pedidoResponse);
        when(pedidoUseCasePortOut.listarPedidos()).thenReturn(pedidosResponse);

        List<PedidoResponse> response = pedidoController.listarPedidos();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(pedidoUseCasePortOut, times(1)).listarPedidos();
    }
}
