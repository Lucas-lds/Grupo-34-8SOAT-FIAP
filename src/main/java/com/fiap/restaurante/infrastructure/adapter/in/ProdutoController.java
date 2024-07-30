package com.fiap.restaurante.infrastructure.adapter.in;

import com.fiap.restaurante.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.restaurante.infrastructure.adapter.in.request.ProdutoRequest;
import com.fiap.restaurante.infrastructure.adapter.in.response.ProdutoResponse;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoUseCasePortOut produtoUseCasePortOut;

    public ProdutoController(ProdutoUseCasePortOut produtoUseCasePortOut){
        this.produtoUseCasePortOut = produtoUseCasePortOut;
    }
    
    @GetMapping
    public List<ProdutoResponse> listarProdutos() {
        return produtoUseCasePortOut.listarProdutos();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id) {
        produtoUseCasePortOut.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    public ProdutoResponse listarProdutoPorCategoria(@PathVariable String categoria) {
        return produtoUseCasePortOut.listarProdutoPorCategoria(categoria);
    }

    @GetMapping("/{id}")
    public ProdutoResponse listarProdutoPorId(@PathVariable Integer id) {
        return produtoUseCasePortOut.listarProdutoPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizarProduto(@PathVariable Integer id, @RequestBody ProdutoRequest produto) throws BadRequestException {
        return produtoUseCasePortOut.atualizarProduto(id, produto);
    }

    @PostMapping
    public ProdutoResponse criarProduto(@RequestBody ProdutoRequest produto) {
        return produtoUseCasePortOut.criarProduto(produto);
    }
}
