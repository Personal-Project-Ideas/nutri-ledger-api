package io.github.pratesjr.nutriledgerapi.domain.errors;

public class AuthTokenGenerationException extends RuntimeException {
    public static final String CODE = "http_401_002";
    public AuthTokenGenerationException() {
        super(ErrorCodes.CODES.get(CODE).description);
    }
    public AuthTokenGenerationException(String customMessage) {
        super(customMessage);
    }
}
