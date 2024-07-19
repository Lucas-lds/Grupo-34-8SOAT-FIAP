package com.fiap.restaurante.application.port.out;

import java.util.List;
import java.util.Optional;

import com.fiap.restaurante.core.domain.Produto;

public interface ProdutoRepository {
    List<Produto> findAll();
    List<Produto> findByCategoria(String categoria);
    Optional<Produto> findById(Long id);
    Produto save(Produto produto);
    void deleteById(Long id);
}
