package io.github.pratesjr.nutriledgerapi.http.controllers;

import io.github.pratesjr.nutriledgerapi.domain.errors.UserConflictException;
import io.github.pratesjr.nutriledgerapi.domain.errors.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private String getRequestId(HttpServletRequest request) {
        Object attr = request.getAttribute("requestId");
        return attr != null ? attr.toString() : UUID.randomUUID().toString();
    }

    private Map<String, Object> buildErrorBody(HttpServletRequest request, String code, int status, String message, List<Map<String, String>> details) {
        Map<String, Object> body = new HashMap<>();
        body.put("requestid", getRequestId(request));
        body.put("code", code);
        body.put("status", status);
        body.put("message", message);
        if (details != null) body.put("details", details);
        return body;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<Map<String, String>> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> err = new HashMap<>();
                    err.put("field", error.getField());
                    err.put("message", error.getDefaultMessage());
                    return err;
                })
                .collect(Collectors.toList());
        Map<String, Object> body = buildErrorBody(request, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST.value(), "Validation failed", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        Map<String, Object> body = buildErrorBody(request, ex.getReason() != null ? ex.getReason() : "RESPONSE_STATUS_ERROR", ex.getStatusCode().value(), ex.getMessage(), null);
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(UserConflictException.class)
    public ResponseEntity<Map<String, Object>> handleUserConflict(UserConflictException ex, HttpServletRequest request) {
        ErrorCodes.ErrorCode codeObj = ErrorCodes.CODES.get(UserConflictException.CODE);
        Map<String, Object> body = buildErrorBody(
            request,
            codeObj.code,
            codeObj.httpStatus,
            ex.getMessage(),
            null
        );
        return ResponseEntity.status(codeObj.httpStatus).body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeExceptions(RuntimeException ex, HttpServletRequest request) {
        // Tenta mapear pelo code se for uma exception customizada
        String code = null;
        int httpStatus = 500;
        String description = ex.getMessage();
        // Reflection para buscar campo CODE
        try {
            java.lang.reflect.Field codeField = ex.getClass().getDeclaredField("CODE");
            codeField.setAccessible(true);
            Object codeValue = codeField.get(ex);
            if (codeValue instanceof String c) {
                ErrorCodes.ErrorCode errorCode = ErrorCodes.CODES.get(c);
                if (errorCode != null) {
                    code = errorCode.code;
                    httpStatus = errorCode.httpStatus;
                    description = errorCode.description;
                } else {
                    code = c;
                }
            }
        } catch (Exception ignore) {
            // Não é uma exception customizada, usa defaults
        }
        if (code == null) {
            code = "http_500_001";
        }
        Map<String, Object> body = buildErrorBody(request, code, httpStatus, description, null);
        return ResponseEntity.status(httpStatus).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex, HttpServletRequest request) {
        Map<String, Object> body = buildErrorBody(request, "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
