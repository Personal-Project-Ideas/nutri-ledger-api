package io.github.pratesjr.nutriledgerapi.infra.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AllowedUserEntityTest {
    @Test
    public void testAllowedUserEntityFields() {
        AllowedUserEntity allowedUserEntity = new AllowedUserEntity();
        allowedUserEntity.setEmail("user@email.com");
        assertNull(allowedUserEntity.getId());
        assertEquals("user@email.com", allowedUserEntity.getEmail());
    }

    @Test
    public void testAllArgsConstructor() {
        AllowedUserEntity entity = new AllowedUserEntity(10L, "test@example.com");
        assertEquals(10L, entity.getId());
        assertEquals("test@example.com", entity.getEmail());
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        AllowedUserEntity entity = new AllowedUserEntity();
        entity.setId(2L);
        entity.setEmail("test@example.com");
        assertEquals(2L, entity.getId());
        assertEquals("test@example.com", entity.getEmail());
    }

}
