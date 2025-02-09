package com.fiap.restaurante.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.application.port.out.ClienteAdapterPortOut;
import com.fiap.restaurante.application.port.out.CognitoAdapterPortOut;
import com.fiap.restaurante.application.port.out.ValidarCpfLambdaPortOut;
import com.fiap.restaurante.core.domain.Cliente;
import com.fiap.restaurante.core.dto.ClienteCognitoRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteAdapterPortOut clienteAdapterPortOut;

    @Mock
    private CognitoAdapterPortOut cognitoAdapterPortOut;

    @Mock
    private ValidarCpfLambdaPortOut validarCpfLambdaPortOut;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "123.456.789-00", "João", "joao@example.com", "12345");
    }

    @Test
    void cadastrarDeveCadastrarClienteComSucesso() {
        doNothing().when(cognitoAdapterPortOut).cadastrarClienteCognito(any(ClienteCognitoRequestDTO.class));
        when(clienteAdapterPortOut.cadastrar(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.cadastrar(cliente);

        assertNotNull(resultado);
        assertEquals(cliente.getCpf(), resultado.getCpf());
        verify(cognitoAdapterPortOut, times(1)).cadastrarClienteCognito(any(ClienteCognitoRequestDTO.class));
        verify(clienteAdapterPortOut, times(1)).cadastrar(any(Cliente.class));
    }

    @Test
    void buscarDeveRetornarClienteQuandoEncontrado() {
        when(clienteAdapterPortOut.buscar(anyString())).thenReturn(cliente);

        Cliente resultado = clienteService.buscar("123.456.789-00");

        assertNotNull(resultado);
        assertEquals("123.456.789-00", resultado.getCpf());
        verify(clienteAdapterPortOut, times(1)).buscar(anyString());
    }

    @Test
    void autenticarClienteDeveValidarCpfComSucesso() {
        doNothing().when(validarCpfLambdaPortOut).validarCpf(anyString());

        clienteService.autenticarCliente("123.456.789-00");

        verify(validarCpfLambdaPortOut, times(1)).validarCpf(anyString());
    }
}
