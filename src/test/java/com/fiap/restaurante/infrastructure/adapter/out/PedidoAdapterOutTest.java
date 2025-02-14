package com.fiap.restaurante.infrastructure.adapter.out;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;
import com.fiap.restaurante.core.domain.PedidoProduto;
import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.request.StatusRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.PedidoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PedidoAdapterOutTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PedidoAdapterOut pedidoAdapterOut;

    @Value("${pedido.service.url}")
    private String pedidoServiceUrl;

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
    void atualizarStatusPedidoDeveAtualizarStatusComSucesso() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        StatusRequest statusRequest = new StatusRequest(3); // Código de status DONE
        HttpEntity<StatusRequest> requestEntity = new HttpEntity<>(statusRequest, headers);
        PedidoResponse pedidoResponse = new PedidoResponse(pedidoId, "DONE", 1L, List.of());
        ResponseEntity<String> response = new ResponseEntity<>(new ObjectMapper().writeValueAsString(pedidoResponse), HttpStatus.OK);

        when(restTemplate.exchange(eq(pedidoServiceUrl + "/" + pedidoId), eq(HttpMethod.PUT), eq(requestEntity), eq(String.class)))
                .thenReturn(response);

        Pedido resultado = pedidoAdapterOut.atualizarStatusPedido(OrderStatus.DONE, pedidoId);

        assertNotNull(resultado);
        assertEquals(pedidoId, resultado.getId());
        verify(restTemplate, times(1)).exchange(eq(pedidoServiceUrl + "/" + pedidoId), eq(HttpMethod.PUT), eq(requestEntity), eq(String.class));
    }

    @Test
    void atualizarStatusPedidoDeveLancarExcecaoQuandoErro() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        StatusRequest statusRequest = new StatusRequest(3); // Código de status DONE
        HttpEntity<StatusRequest> requestEntity = new HttpEntity<>(statusRequest, headers);
        ResponseEntity<String> response = new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);

        when(restTemplate.exchange(eq(pedidoServiceUrl + "/" + pedidoId), eq(HttpMethod.PUT), eq(requestEntity), eq(String.class)))
                .thenReturn(response);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoAdapterOut.atualizarStatusPedido(OrderStatus.DONE, pedidoId);
        });

        assertEquals("Erro ao atualizar o status do pedido: Erro", exception.getMessage());
        verify(restTemplate, times(1)).exchange(eq(pedidoServiceUrl + "/" + pedidoId), eq(HttpMethod.PUT), eq(requestEntity), eq(String.class));
    }

    @Test
    void checkoutPedidoDeveRealizarCheckoutComSucesso() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        PedidoRequest pedidoRequest = new PedidoRequest(1L, List.of());
        HttpEntity<PedidoRequest> requestEntity = new HttpEntity<>(pedidoRequest, headers);
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.CREATED);

        when(restTemplate.postForEntity(eq(pedidoServiceUrl), eq(requestEntity), eq(String.class)))
                .thenReturn(response);

        Pedido resultado = pedidoAdapterOut.checkoutPedido(pedido);

        assertNotNull(resultado);
        assertEquals(pedido.getId(), resultado.getId());
        verify(restTemplate, times(1)).postForEntity(eq(pedidoServiceUrl), eq(requestEntity), eq(String.class));
    }

    @Test
    void checkoutPedidoDeveLancarExcecaoQuandoErro() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        PedidoRequest pedidoRequest = new PedidoRequest(1L, List.of());
        HttpEntity<PedidoRequest> requestEntity = new HttpEntity<>(pedidoRequest, headers);
        ResponseEntity<String> response = new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);

        when(restTemplate.postForEntity(eq(pedidoServiceUrl), eq(requestEntity), eq(String.class)))
                .thenReturn(response);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoAdapterOut.checkoutPedido(pedido);
        });

        assertEquals("Erro ao realizar o checkout do pedido: Erro", exception.getMessage());
        verify(restTemplate, times(1)).postForEntity(eq(pedidoServiceUrl), eq(requestEntity), eq(String.class));
    }

    @Test
    void listarPedidoPorIdDeveRetornarPedidoComSucesso() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        PedidoResponse pedidoResponse = new PedidoResponse(pedidoId, "RECEIVED", 1L, List.of());
        ResponseEntity<PedidoResponse> response = new ResponseEntity<>(pedidoResponse, HttpStatus.OK);

        when(restTemplate.exchange(eq(pedidoServiceUrl + "/" + pedidoId), eq(HttpMethod.GET), eq(requestEntity), eq(PedidoResponse.class)))
                .thenReturn(response);

        Pedido resultado = pedidoAdapterOut.listarPedidoPorId(pedidoId);

        assertNotNull(resultado);
        assertEquals(pedidoId, resultado.getId());
        verify(restTemplate, times(1)).exchange(eq(pedidoServiceUrl + "/" + pedidoId), eq(HttpMethod.GET), eq(requestEntity), eq(PedidoResponse.class));
    }

    @Test
    void listarPedidoPorIdDeveLancarExcecaoQuandoErro() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<PedidoResponse> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        when(restTemplate.exchange(eq(pedidoServiceUrl + "/" + pedidoId), eq(HttpMethod.GET), eq(requestEntity), eq(PedidoResponse.class)))
                .thenReturn(response);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoAdapterOut.listarPedidoPorId(pedidoId);
        });

        assertEquals("Erro ao buscar o pedido por ID: null", exception.getMessage());
        verify(restTemplate, times(1)).exchange(eq(pedidoServiceUrl + "/" + pedidoId), eq(HttpMethod.GET), eq(requestEntity), eq(PedidoResponse.class));
    }

    @Test
    void listarPedidosDeveRetornarListaDePedidosComSucesso() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        PedidoResponse[] pedidoResponses = {new PedidoResponse(pedidoId, "RECEIVED", 1L, List.of())};
        ResponseEntity<PedidoResponse[]> response = new ResponseEntity<>(pedidoResponses, HttpStatus.OK);

        when(restTemplate.exchange(eq(pedidoServiceUrl), eq(HttpMethod.GET), eq(requestEntity), eq(PedidoResponse[].class)))
                .thenReturn(response);

        List<Pedido> resultado = pedidoAdapterOut.listarPedidos();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(restTemplate, times(1)).exchange(eq(pedidoServiceUrl), eq(HttpMethod.GET), eq(requestEntity), eq(PedidoResponse[].class));
    }

    @Test
    void listarPedidosDeveLancarExcecaoQuandoErro() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<PedidoResponse[]> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        when(restTemplate.exchange(eq(pedidoServiceUrl), eq(HttpMethod.GET), eq(requestEntity), eq(PedidoResponse[].class)))
                .thenReturn(response);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoAdapterOut.listarPedidos();
        });

        assertEquals("Erro ao listar os pedidos: null", exception.getMessage());
        verify(restTemplate, times(1)).exchange(eq(pedidoServiceUrl), eq(HttpMethod.GET), eq(requestEntity), eq(PedidoResponse[].class));
    }
}
