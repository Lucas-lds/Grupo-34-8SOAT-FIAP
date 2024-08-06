package com.fiap.restaurante.infrastructure.adapter.in.validation;

import com.fiap.restaurante.core.domain.Category;
import com.fiap.restaurante.infrastructure.exception.CategoriaInvalidaException;

public class ProdutoValidation {
    
    public static void validate(String categoria) {
        if (!isValidCategory(categoria)) {
            throw new CategoriaInvalidaException("Categoria inválida: " + categoria);
        }
    }

    public static boolean isValidCategory(String category) {
        for (Category cat : Category.values()) {
            if (cat.name().equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }
}
