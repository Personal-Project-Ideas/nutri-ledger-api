package io.github.pratesjr.nutriledgerapi.domain.errors;

public class UserNotFoundException extends RuntimeException {
    public static final String CODE = "http_404_002";
    public UserNotFoundException() {
        super(ErrorCodes.CODES.get(CODE).description);
    }
    public UserNotFoundException(String customMessage) {
        super(customMessage);
    }
}

