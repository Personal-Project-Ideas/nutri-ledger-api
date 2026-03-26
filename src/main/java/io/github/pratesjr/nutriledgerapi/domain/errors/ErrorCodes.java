package io.github.pratesjr.nutriledgerapi.domain.errors;

import java.util.Map;
import java.util.HashMap;

public final class ErrorCodes {
    private ErrorCodes() {}

    public static final Map<String, ErrorCode> CODES = new HashMap<>() {{
        put("http_400_001", new ErrorCode("http_400_001", 400, "Validation failed for one or more fields"));
        put("http_400_002", new ErrorCode("http_400_002", 400, "Malformed request body or parameters"));
        put("http_401_001", new ErrorCode("http_401_001", 401, "Authentication is required or has failed"));
        put("http_403_001", new ErrorCode("http_403_001", 403, "You do not have permission to access this resource"));
        put("http_404_001", new ErrorCode("http_404_001", 404, "Resource not found"));
        put("http_409_001", new ErrorCode("http_409_001", 409, "Resource conflict or already exists"));
        put("http_422_001", new ErrorCode("http_422_001", 422, "Request could not be processed"));
        put("http_500_001", new ErrorCode("http_500_001", 500, "An unexpected internal error occurred"));
        put("http_501_001", new ErrorCode("http_501_001", 501, "Not implemented"));
        put("http_503_001", new ErrorCode("http_503_001", 503, "Service is temporarily unavailable"));
    }};

    public static class ErrorCode {
        public final String code;
        public final int httpStatus;
        public final String description;
        public ErrorCode(String code, int httpStatus, String description) {
            this.code = code;
            this.httpStatus = httpStatus;
            this.description = description;
        }
    }
}
