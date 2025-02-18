package com.fiap.restaurante.core.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PaymentStatusTest {

    @Test
    void testEnumValues() {
        // Verifica se todos os valores da enum estão presentes
        assertArrayEquals(new PaymentStatus[]{PaymentStatus.PENDING, PaymentStatus.APPROVED, PaymentStatus.EXPIRED}, PaymentStatus.values());
    }

    @Test
    void testEnumName() {
        // Verifica se o nome de cada constante é o esperado
        assertEquals("PENDING", PaymentStatus.PENDING.name());
        assertEquals("APPROVED", PaymentStatus.APPROVED.name());
        assertEquals("EXPIRED", PaymentStatus.EXPIRED.name());
    }

    @Test
    void testEnumOrdinal() {
        // Verifica os valores de ordinal de cada constante
        assertEquals(0, PaymentStatus.PENDING.ordinal());
        assertEquals(1, PaymentStatus.APPROVED.ordinal());
        assertEquals(2, PaymentStatus.EXPIRED.ordinal());
    }

    @Test
    void testEnumValueOf() {
        // Verifica se o método valueOf retorna as instâncias corretas
        assertEquals(PaymentStatus.PENDING, PaymentStatus.valueOf("PENDING"));
        assertEquals(PaymentStatus.APPROVED, PaymentStatus.valueOf("APPROVED"));
        assertEquals(PaymentStatus.EXPIRED, PaymentStatus.valueOf("EXPIRED"));
    }
}
