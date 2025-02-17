package com.fiap.restaurante.infrastructure.adapter.in;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Assertions;

import com.fiap.restaurante.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.restaurante.core.domain.Produto;
import com.fiap.restaurante.infrastructure.adapter.in.request.ProdutoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.ProdutoResponse;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    @Mock
    private ProdutoUseCasePortOut produtoUseCasePortOut;

    @InjectMocks
    private ProdutoController produtoController;

    private Produto produto;
    private ProdutoRequest produtoRequest;
    private ProdutoResponse produtoResponse;

    @BeforeEach
    void setUp() {
        produto = new Produto(1L, "Produto Teste", "Categoria Teste", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
        produtoRequest = new ProdutoRequest("Produto Teste", "Categoria Teste", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
        produtoResponse = ProdutoResponse.fromDomain(produto);
    }

    @Test
    void listarProdutosDeveRetornarListaDeProdutosComSucesso() {
        List<Produto> produtos = List.of(produto);
        when(produtoUseCasePortOut.listarProdutos()).thenReturn(produtos);

        List<Produto> response = produtoController.listarProdutos();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(produtoUseCasePortOut, times(1)).listarProdutos();
    }

    @Test
    void listarProdutosDeveRetornarListaVaziaQuandoNenhumProduto() {
        when(produtoUseCasePortOut.listarProdutos()).thenReturn(List.of());

        List<Produto> response = produtoController.listarProdutos();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void listarProdutosDeveLancarExcecaoQuandoErroDeConexao() {
        when(produtoUseCasePortOut.listarProdutos()).thenThrow(new RuntimeException("Erro de conexão com o banco de dados"));

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            produtoController.listarProdutos();
        });

        assertEquals("Erro de conexão com o banco de dados", exception.getMessage());
    }

    @Test
    void buscarPorCategoriaDeveRetornarListaDeProdutosComSucesso() {
        List<Produto> produtos = List.of(produto);
        when(produtoUseCasePortOut.listarProdutoPorCategoria(anyString())).thenReturn(produtos);

        List<Produto> response = produtoController.buscarPorCategoria("Categoria Teste");

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(produtoUseCasePortOut, times(1)).listarProdutoPorCategoria(anyString());
    }

    @Test
    void cadastrarProdutoDeveCadastrarProdutoComSucesso() {
        when(produtoUseCasePortOut.criarProduto(any(Produto.class))).thenReturn(produto);

        ResponseEntity<ProdutoResponse> response = produtoController.cadastrarProduto(produtoRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produtoResponse, response.getBody());
        verify(produtoUseCasePortOut, times(1)).criarProduto(any(Produto.class));
    }

    @Test
    void cadastrarProdutoDeveLancarExcecaoQuandoNomeVazio() {
        produtoRequest = new ProdutoRequest("", "Categoria Teste", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");

        when(produtoUseCasePortOut.criarProduto(any(Produto.class)))
            .thenThrow(new IllegalArgumentException("Nome do produto não pode ser vazio"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            produtoController.cadastrarProduto(produtoRequest);
        });

        assertEquals("Nome do produto não pode ser vazio", exception.getMessage());
        verify(produtoUseCasePortOut, times(1)).criarProduto(any(Produto.class));
    }


    @Test
    void cadastrarProdutoDeveLancarExcecaoQuandoPrecoInvalido() {
        produtoRequest = new ProdutoRequest("Produto Teste", "Categoria Teste", -100.0, "Descricao Teste", "http://imagem.url/teste.jpg");

        when(produtoUseCasePortOut.criarProduto(any(Produto.class)))
            .thenThrow(new IllegalArgumentException("Preço do produto deve ser maior que zero"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            produtoController.cadastrarProduto(produtoRequest);
        });

        assertEquals("Preço do produto deve ser maior que zero", exception.getMessage());
        verify(produtoUseCasePortOut, times(1)).criarProduto(any(Produto.class));
    }


    @Test
    void atualizarProdutoDeveAtualizarProdutoComSucesso() throws BadRequestException {
        when(produtoUseCasePortOut.atualizarProduto(anyLong(), any(Produto.class))).thenReturn(produto);

        ResponseEntity<ProdutoResponse> response = produtoController.atualizarProduto(1L, produtoRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoResponse, response.getBody());
        verify(produtoUseCasePortOut, times(1)).atualizarProduto(anyLong(), any(Produto.class));
    }

    @Test
    void atualizarProdutoDeveLancarExcecaoQuandoProdutoNaoEncontrado() throws BadRequestException {
        when(produtoUseCasePortOut.atualizarProduto(anyLong(), any(Produto.class)))
            .thenThrow(new BadRequestException("Produto não encontrado"));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            produtoController.atualizarProduto(1L, produtoRequest);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
    }

    @Test
    void atualizarProdutoDeveLancarExcecaoQuandoErroDeConexao() throws BadRequestException {
        when(produtoUseCasePortOut.atualizarProduto(anyLong(), any(Produto.class)))
            .thenThrow(new RuntimeException("Erro de conexão com o banco de dados"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoController.atualizarProduto(1L, produtoRequest);
        });

        assertEquals("Erro de conexão com o banco de dados", exception.getMessage());
    }

    @Test
    void deletarPorIdDeveDeletarProdutoComSucesso() {
        doNothing().when(produtoUseCasePortOut).deletarPorId(anyLong());

        ResponseEntity<Void> response = produtoController.deletarPorId(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(produtoUseCasePortOut, times(1)).deletarPorId(anyLong());
    }

    @Test
    void deletarPorIdDeveLancarExcecaoQuandoErroDeConexao() {

        doThrow(new RuntimeException("Erro de conexão com o banco de dados"))
            .when(produtoUseCasePortOut).deletarPorId(anyLong());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoController.deletarPorId(1L);
        });

        assertEquals("Erro de conexão com o banco de dados", exception.getMessage());
    }
}
