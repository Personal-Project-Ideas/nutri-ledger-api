package io.github.pratesjr.nutriledgerapi.domain.errors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorCodesTest {
    @Test
    void allCodesShouldBeMappedCorrectly() {
        ErrorCodes.ErrorCode code = ErrorCodes.CODES.get("http_409_001");
        assertNotNull(code);
        assertEquals("http_409_001", code.code);
        assertEquals(409, code.httpStatus);
        assertEquals("Resource conflict or already exists", code.description);
    }

    @Test
    void codesShouldNotBeNullOrDuplicated() {
        ErrorCodes.CODES.forEach((key, value) -> {
            assertNotNull(key);
            assertNotNull(value);
            assertEquals(key, value.code);
        });
    }
}

