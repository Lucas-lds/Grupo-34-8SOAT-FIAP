package com.fiap.restaurante.infrastructure.adapter.out;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.core.domain.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProdutoAdapterOutTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProdutoAdapterOut produtoAdapterOut;

    @Value("${produto.microservice.url}")
    private String microserviceUrl;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto(1L, "Produto Teste", "Categoria Teste", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
    }

    @Test
    void listarProdutosDeveRetornarListaDeProdutos() {
        Produto[] produtos = {produto};
        when(restTemplate.getForObject(eq(microserviceUrl + "/produtos"), eq(Produto[].class))).thenReturn(produtos);

        List<Produto> resultado = produtoAdapterOut.listarProdutos();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(restTemplate, times(1)).getForObject(eq(microserviceUrl + "/produtos"), eq(Produto[].class));
    }

    @Test
    void listarProdutoPorCategoriaDeveRetornarListaDeProdutos() {
        Produto[] produtos = {produto};
        when(restTemplate.getForObject(eq(microserviceUrl + "/produtos/categoria/" + produto.getCategoria()), eq(Produto[].class))).thenReturn(produtos);

        List<Produto> resultado = produtoAdapterOut.listarProdutoPorCategoria(produto.getCategoria());

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(restTemplate, times(1)).getForObject(eq(microserviceUrl + "/produtos/categoria/" + produto.getCategoria()), eq(Produto[].class));
    }

    @Test
    void criarProdutoDeveRetornarProdutoCriado() {
        when(restTemplate.postForObject(eq(microserviceUrl + "/produtos/cadastrar"), any(Produto.class), eq(Produto.class))).thenReturn(produto);

        Produto resultado = produtoAdapterOut.criarProduto(produto);

        assertNotNull(resultado);
        assertEquals(produto.getIdProduto(), resultado.getIdProduto());
        verify(restTemplate, times(1)).postForObject(eq(microserviceUrl + "/produtos/cadastrar"), any(Produto.class), eq(Produto.class));
    }

    @Test
    void atualizarProdutoDeveRetornarProdutoAtualizado() {
        doNothing().when(restTemplate).put(eq(microserviceUrl + "/produtos/" + produto.getIdProduto()), any(Produto.class));

        Produto resultado = produtoAdapterOut.atualizarProduto(produto.getIdProduto(), produto);

        assertNotNull(resultado);
        assertEquals(produto.getIdProduto(), resultado.getIdProduto());
        verify(restTemplate, times(1)).put(eq(microserviceUrl + "/produtos/" + produto.getIdProduto()), any(Produto.class));
    }

    @Test
    void deletarPorIdDeveDeletarProduto() {
        doNothing().when(restTemplate).delete(eq(microserviceUrl + "/produtos/" + produto.getIdProduto()));

        produtoAdapterOut.deletarPorId(produto.getIdProduto());

        verify(restTemplate, times(1)).delete(eq(microserviceUrl + "/produtos/" + produto.getIdProduto()));
    }
}
