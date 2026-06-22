package io.github.pratesjr.nutriledgerapi.domain.errors;

public class UserNotAllowedToBeCreatedException extends RuntimeException {
    public static final String CODE = "http_403_002";
    public UserNotAllowedToBeCreatedException() {
        super(ErrorCodes.CODES.get(CODE).description);
    }
    public UserNotAllowedToBeCreatedException(String customMessage) {
        super(customMessage);
    }
}
