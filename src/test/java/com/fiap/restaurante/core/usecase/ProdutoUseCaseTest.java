package com.fiap.restaurante.core.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.application.port.out.ProdutoServicePortOut;
import com.fiap.restaurante.core.domain.Produto;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProdutoUseCaseTest {

    @Mock
    private ProdutoServicePortOut produtoService;

    @InjectMocks
    private ProdutoUseCase produtoUseCase;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto(1L, "Produto Teste", "Categoria Teste", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
    }

    @Test
    void listarProdutosDeveRetornarListaDeProdutos() {
        List<Produto> produtos = List.of(produto);
        when(produtoService.listarProdutos()).thenReturn(produtos);

        List<Produto> resultado = produtoUseCase.listarProdutos();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(produtoService, times(1)).listarProdutos();
    }

    @Test
    void listarProdutoPorCategoriaDeveRetornarListaDeProdutos() {
        List<Produto> produtos = List.of(produto);
        when(produtoService.listarProdutoPorCategoria(anyString())).thenReturn(produtos);

        List<Produto> resultado = produtoUseCase.listarProdutoPorCategoria("Categoria Teste");

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(produtoService, times(1)).listarProdutoPorCategoria(anyString());
    }

    @Test
    void criarProdutoDeveRetornarProdutoCriado() {
        when(produtoService.criarProduto(any(Produto.class))).thenReturn(produto);

        Produto resultado = produtoUseCase.criarProduto(produto);

        assertNotNull(resultado);
        assertEquals(produto, resultado);
        verify(produtoService, times(1)).criarProduto(any(Produto.class));
    }

    @Test
    void atualizarProdutoDeveRetornarProdutoAtualizado() throws BadRequestException {
        when(produtoService.atualizarProduto(anyLong(), any(Produto.class))).thenReturn(produto);

        Produto resultado = produtoUseCase.atualizarProduto(1L, produto);

        assertNotNull(resultado);
        assertEquals(produto, resultado);
        verify(produtoService, times(1)).atualizarProduto(anyLong(), any(Produto.class));
    }

    @Test
    void deletarPorIdDeveDeletarProduto() {
        doNothing().when(produtoService).deletarPorId(anyLong());

        produtoUseCase.deletarPorId(1L);

        verify(produtoService, times(1)).deletarPorId(anyLong());
    }
}
