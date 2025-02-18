package com.fiap.restaurante.infrastructure.adapter.out;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.restaurante.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.request.StatusRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.PedidoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fiap.restaurante.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.core.domain.OrderStatus;
import com.fiap.restaurante.core.domain.Pedido;
import com.fiap.restaurante.core.domain.PedidoProduto;

@Component
public class PedidoAdapterOut implements PedidoAdapterPortOut {

    private final RestTemplate restTemplate;

    @Value("${pedido.service.url}")
    private String pedidoServiceUrl;

    public PedidoAdapterOut(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Pedido atualizarStatusPedido(OrderStatus status, UUID id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        StatusRequest statusRequest = new StatusRequest(status.getStatusCode());
        HttpEntity<StatusRequest> requestEntity = new HttpEntity<>(statusRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(pedidoServiceUrl + "/" + id, HttpMethod.PUT, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseBody, Pedido.class);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao converter a resposta JSON para o objeto Pedido: " + e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("Erro ao atualizar o status do pedido: " + response.getBody());
        }
    }

    @Override
    public Pedido checkoutPedido(Pedido pedido) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var pedidoRequest = new PedidoRequest(pedido.getIdCliente(), pedido.getListaPedidoProduto().stream().map(PedidoProduto::toRequest).collect(Collectors.toList()));

        HttpEntity<PedidoRequest> requestEntity = new HttpEntity<>(pedidoRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(pedidoServiceUrl, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return pedido;
        } else {
            throw new RuntimeException("Erro ao realizar o checkout do pedido: " + response.getBody());
        }
    }

    @Override
    public Pedido listarPedidoPorId(UUID id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<PedidoResponse> response = restTemplate.exchange(pedidoServiceUrl + "/" + id, HttpMethod.GET, requestEntity, PedidoResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            PedidoResponse pedidoResponse = response.getBody();
            assert pedidoResponse != null;
            return PedidoResponse.toDomain(pedidoResponse);
        } else {
            throw new RuntimeException("Erro ao buscar o pedido por ID: " + response.getBody());
        }
    }

    @Override
    public List<Pedido> listarPedidos() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<PedidoResponse[]> response = restTemplate.exchange(pedidoServiceUrl, HttpMethod.GET, requestEntity, PedidoResponse[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            PedidoResponse[] pedidoResponses = response.getBody();
            assert pedidoResponses != null;
            return Stream.of(pedidoResponses)
                    .map(PedidoResponse::toDomain)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Erro ao listar os pedidos: " + Arrays.toString(response.getBody()));
        }
    }
}
