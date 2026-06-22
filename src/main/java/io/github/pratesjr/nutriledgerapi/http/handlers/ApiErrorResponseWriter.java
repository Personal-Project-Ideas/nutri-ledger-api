package io.github.pratesjr.nutriledgerapi.http.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.pratesjr.nutriledgerapi.domain.errors.ErrorCodes;
import io.github.pratesjr.nutriledgerapi.infra.config.RequestIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiErrorResponseWriter {

    private final ObjectMapper objectMapper;

    public ApiErrorResponseWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void write(HttpServletRequest request, HttpServletResponse response, String errorCodeKey)
            throws IOException {
        ErrorCodes.ErrorCode errorCode = ErrorCodes.CODES.get(errorCodeKey);
        if (errorCode == null) {
            throw new IllegalArgumentException("Unknown error code: " + errorCodeKey);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("requestid", resolveRequestId(request));
        body.put("code", errorCode.code);
        body.put("status", errorCode.httpStatus);
        body.put("message", errorCode.description);

        response.setStatus(errorCode.httpStatus);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), body);
    }

    private static String resolveRequestId(HttpServletRequest request) {
        Object attr = request.getAttribute(RequestIdFilter.REQUEST_ID_ATTR);
        return attr != null ? attr.toString() : UUID.randomUUID().toString();
    }
}
