package com.fiap.restaurante.core.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.core.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClienteUseCaseTest {

    @Mock
    private ClienteServicePortOut clienteService;

    @InjectMocks
    private ClienteUseCase clienteUseCase;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "123.456.789-00", "João", "joao@example.com", "12345");
    }

    @Test
    void buscarClienteDeveRetornarClienteQuandoEncontrado() {
        when(clienteService.buscar(anyString())).thenReturn(cliente);

        Cliente resultado = clienteUseCase.buscarCliente("123.456.789-00");

        assertNotNull(resultado);
        assertEquals("123.456.789-00", resultado.getCpf());
        verify(clienteService, times(1)).buscar(anyString());
    }

    @Test
    void cadastrarClienteDeveCadastrarClienteComSucesso() {
        when(clienteService.cadastrar(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteUseCase.cadastrarCliente(cliente);

        assertNotNull(resultado);
        assertEquals(cliente.getCpf(), resultado.getCpf());
        verify(clienteService, times(1)).cadastrar(any(Cliente.class));
    }

    @Test
    void validarAutenticacaoClienteDeveValidarCpfComSucesso() {
        doNothing().when(clienteService).autenticarCliente(anyString());

        clienteUseCase.validarAutenticacaoCliente("123.456.789-00");

        verify(clienteService, times(1)).autenticarCliente(anyString());
    }
}
