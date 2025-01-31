package com.fiap.restaurante.infrastructure.adapter.out;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.fiap.restaurante.application.port.out.ProdutoAdapterPortOut;
import com.fiap.restaurante.core.domain.Produto;

@Component
public class ProdutoAdapterOut implements ProdutoAdapterPortOut {

    private final RestTemplate restTemplate;

    @Value("${produto.microservice.url}")
    private String microserviceUrl;

    public ProdutoAdapterOut(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Produto> listarProdutos() {
        Produto[] produtos = restTemplate.getForObject(microserviceUrl + "/produtos", Produto[].class);
        return List.of(produtos);
    }

    @Override
    public List<Produto> listarProdutoPorCategoria(String categoria) {
        //ProdutoValidation.validate(categoria);
        Produto[] produtos = restTemplate.getForObject(microserviceUrl + "/produtos/categoria/" + categoria, Produto[].class);
        return List.of(produtos);
    }

    @Override
    public Produto criarProduto(Produto produto) {
        //ProdutoValidation.validateFields(produto);
        return restTemplate.postForObject(microserviceUrl + "/produtos/cadastrar", produto, Produto.class);
    }

    @Override
    public Produto atualizarProduto(Long id, Produto produto) {
        //ProdutoValidation.validateFields(produto);
        restTemplate.put(microserviceUrl + "/produtos/" + id, produto);
        return produto; // Assuming the product is returned after update
    }

    @Override
    public void deletarPorId(Long id) {
        restTemplate.delete(microserviceUrl + "/produtos/" + id);
    }
}
