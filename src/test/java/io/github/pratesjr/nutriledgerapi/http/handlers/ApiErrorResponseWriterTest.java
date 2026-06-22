package io.github.pratesjr.nutriledgerapi.http.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pratesjr.nutriledgerapi.infra.config.RequestIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiErrorResponseWriterTest {

    private final ApiErrorResponseWriter writer = new ApiErrorResponseWriter(new ObjectMapper());

    @Test
    void shouldWriteJsonBodyWithCorrectStatusAndRequestId() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter body = new StringWriter();
        when(request.getAttribute(RequestIdFilter.REQUEST_ID_ATTR)).thenReturn("req-123");
        when(response.getWriter()).thenReturn(new PrintWriter(body));

        writer.write(request, response, "http_401_001");

        verify(response).setStatus(401);
        String json = body.toString();
        assertTrue(json.contains("req-123"));
        assertTrue(json.contains("http_401_001"));
        assertTrue(json.contains("Authentication is required or has failed"));
    }

    @Test
    void shouldUseGeneratedRequestIdWhenAttributeMissing() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter body = new StringWriter();
        when(request.getAttribute(RequestIdFilter.REQUEST_ID_ATTR)).thenReturn(null);
        when(response.getWriter()).thenReturn(new PrintWriter(body));

        writer.write(request, response, "http_401_001");

        String json = body.toString();
        assertFalse(json.contains("\"requestid\":null"));
        assertTrue(json.contains("requestid"));
    }

    @Test
    void shouldThrowForUnknownErrorCode() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        assertThrows(IllegalArgumentException.class, () -> writer.write(request, response, "unknown_code"));
    }
}
