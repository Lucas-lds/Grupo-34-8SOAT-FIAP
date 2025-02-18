package com.fiap.restaurante.infrastructure.configuration;

import com.fiap.restaurante.application.port.out.ClienteServicePortOut;
import com.fiap.restaurante.application.port.out.ProdutoServicePortOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UseCaseConfigTest {

    @Mock
    private ClienteServicePortOut clienteServicePortOut;

    @Mock
    private ProdutoServicePortOut produtoServicePortOut;

    @InjectMocks
    private UseCaseConfig useCaseConfig;

    @Test
    void shouldCreateClienteUseCaseBean() {
        var bean = useCaseConfig.clienteUseCase(clienteServicePortOut);
        assertNotNull(bean, "ClienteUseCase bean should not be null");
    }

    @Test
    void shouldCreateProdutoUseCaseBean() {
        var bean = useCaseConfig.produtoUseCase(produtoServicePortOut);
        assertNotNull(bean, "ProdutoUseCase bean should not be null");
    }
}
