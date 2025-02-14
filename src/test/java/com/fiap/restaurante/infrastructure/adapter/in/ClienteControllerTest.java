package com.fiap.restaurante.infrastructure.adapter.in;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.application.port.out.usecase.ClienteUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.ClienteRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.ClienteResponse;
import com.fiap.restaurante.core.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private ClienteUseCasePortOut clienteUseCasePortOut;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente;
    private ClienteRequest clienteRequest;
    private ClienteResponse clienteResponse;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "123.456.789-00", "João", "joao@example.com", "12345");
        clienteRequest = new ClienteRequest("123.456.789-00", "João", "joao@example.com", "12345");
        clienteResponse = ClienteResponse.fromDomain(cliente);
    }

    @Test
    void cadastrarClienteDeveCadastrarClienteComSucesso() {
        when(clienteUseCasePortOut.cadastrarCliente(any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<ClienteResponse> response = clienteController.cadastrarCliente(clienteRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(clienteResponse, response.getBody());
        verify(clienteUseCasePortOut, times(1)).cadastrarCliente(any(Cliente.class));
    }

    @Test
    void buscarPorCpfDeveRetornarClienteComSucesso() {
        when(clienteUseCasePortOut.buscarCliente(anyString())).thenReturn(cliente);

        ResponseEntity<ClienteResponse> response = clienteController.buscarPorCpf("123.456.789-00");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteResponse, response.getBody());
        verify(clienteUseCasePortOut, times(1)).buscarCliente(anyString());
    }

    @Test
    void validarUsuarioDeveAutenticarUsuarioComSucesso() {
        doNothing().when(clienteUseCasePortOut).validarAutenticacaoCliente(anyString());

        ResponseEntity<String> response = clienteController.validarUsuario("123.456.789-00");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario autenticado com sucesso!", response.getBody());
        verify(clienteUseCasePortOut, times(1)).validarAutenticacaoCliente(anyString());
    }
}
