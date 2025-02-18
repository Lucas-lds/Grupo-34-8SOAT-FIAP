package com.fiap.restaurante.core.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void shouldHaveFourCategories() {
        assertEquals(4, Category.values().length);
    }

    @Test
    void shouldContainAllExpectedCategories() {
        Category[] categories = Category.values();
        assertAll("Categories",
            () -> assertEquals(Category.LANCHE, categories[0]),
            () -> assertEquals(Category.ACOMPANHAMENTO, categories[1]),
            () -> assertEquals(Category.BEBIDA, categories[2]),
            () -> assertEquals(Category.SOBREMESA, categories[3])
        );
    }

    @Test
    void shouldReturnCorrectCategoryFromValueOf() {
        assertEquals(Category.LANCHE, Category.valueOf("LANCHE"));
        assertEquals(Category.ACOMPANHAMENTO, Category.valueOf("ACOMPANHAMENTO"));
        assertEquals(Category.BEBIDA, Category.valueOf("BEBIDA"));
        assertEquals(Category.SOBREMESA, Category.valueOf("SOBREMESA"));
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {
        assertEquals("LANCHE", Category.LANCHE.toString());
        assertEquals("ACOMPANHAMENTO", Category.ACOMPANHAMENTO.toString());
        assertEquals("BEBIDA", Category.BEBIDA.toString());
        assertEquals("SOBREMESA", Category.SOBREMESA.toString());
    }

    @Test
    void shouldThrowExceptionForInvalidCategory() {
        assertThrows(IllegalArgumentException.class, () -> {
            Category.valueOf("INVALID_CATEGORY");
        });
    }
}
