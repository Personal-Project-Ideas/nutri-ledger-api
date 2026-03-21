package io.github.pratesjr.nutriledgerapi.infra.entities;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PortionEntityTest {
    @Test
    void testPortionEntityFields() {
        Instant now = Instant.now();
        PortionEntity portion = new PortionEntity(
            1L,
            "Arroz",
            new BigDecimal("100.0"),
            "g",
            now,
            now
        );
        assertEquals(1L, portion.getId());
        assertEquals("Arroz", portion.getName());
        assertEquals(new BigDecimal("100.0"), portion.getQuantity());
        assertEquals("g", portion.getUnit());
        assertEquals(now, portion.getCreatedAt());
        assertEquals(now, portion.getUpdatedAt());
    }

    @Test
    void testPortionEntitySetters() {
        PortionEntity portion = new PortionEntity();
        portion.setId(2L);
        portion.setName("Feijão");
        portion.setQuantity(new BigDecimal("50.0"));
        portion.setUnit("g");
        Instant created = Instant.now();
        Instant updated = created.plusSeconds(60);
        portion.setCreatedAt(created);
        portion.setUpdatedAt(updated);
        assertEquals(2L, portion.getId());
        assertEquals("Feijão", portion.getName());
        assertEquals(new BigDecimal("50.0"), portion.getQuantity());
        assertEquals("g", portion.getUnit());
        assertEquals(created, portion.getCreatedAt());
        assertEquals(updated, portion.getUpdatedAt());
    }

    @Test
    void testPortionEntityEqualsAndHashCode() {
        Instant now = Instant.now();
        PortionEntity p1 = new PortionEntity(1L, "Arroz", new BigDecimal("100.0"), "g", now, now);
        PortionEntity p2 = new PortionEntity(1L, "Arroz", new BigDecimal("100.0"), "g", now, now);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testPortionEntityToString() {
        Instant now = Instant.now();
        PortionEntity portion = new PortionEntity(1L, "Arroz", new BigDecimal("100.0"), "g", now, now);
        String str = portion.toString();
        assertTrue(str.contains("Arroz"));
        assertTrue(str.contains("100.0"));
        assertTrue(str.contains("g"));
    }
}

