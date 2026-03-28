package io.github.pratesjr.nutriledgerapi.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SanitizerUtilTest {
    static class TestObj {
        public String cpf = "123.456.789-00";
        public String email = "test@email.com";
        public String password = "secret";
        public String name = "John";
    }

    @Test
    void shouldMaskSensitiveFields() {
        TestObj obj = new TestObj();
        String sanitized = SanitizerUtil.sanitize(obj);
        assertTrue(sanitized.contains("\"cpf\":\"***\""));
        assertTrue(sanitized.contains("\"email\":\"***\""));
        assertTrue(sanitized.contains("\"password\":\"***\""));
        assertTrue(sanitized.contains("\"name\":\"John\""));
    }

    @Test
    void shouldHandleNull() {
        assertNull(SanitizerUtil.sanitize(null));
    }

    @Test
    void shouldFallbackToStringOnError() {
        Object obj = new Object() {
            @Override public String toString() { return "fallback"; }
        };
        assertEquals("fallback", SanitizerUtil.sanitize(obj));
    }
}

