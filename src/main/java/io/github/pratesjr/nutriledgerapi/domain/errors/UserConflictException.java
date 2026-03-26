package io.github.pratesjr.nutriledgerapi.domain.errors;

public class UserConflictException extends RuntimeException {
    public static final String CODE = "http_409_001";
    public UserConflictException() {
        super(ErrorCodes.CODES.get(CODE).description);
    }
    public UserConflictException(String customMessage) {
        super(customMessage);
    }
}

