package io.github.pratesjr.nutriledgerapi.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pratesjr.nutriledgerapi.http.handlers.ApiErrorResponseWriter;
import io.github.pratesjr.nutriledgerapi.infra.config.RequestIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OAuth2LoginFailureHandlerTest {

    private final OAuth2LoginFailureHandler handler =
            new OAuth2LoginFailureHandler(new ApiErrorResponseWriter(new ObjectMapper()));

    @Test
    void shouldReturn401WithRequestId() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter body = new StringWriter();
        when(request.getAttribute(RequestIdFilter.REQUEST_ID_ATTR)).thenReturn("test-request-id");
        when(response.getWriter()).thenReturn(new PrintWriter(body));

        handler.onAuthenticationFailure(request, response, new BadCredentialsException("fail"));

        verify(response).setStatus(401);
        String json = body.toString();
        assertTrue(json.contains("test-request-id"));
        assertTrue(json.contains("http_401_001"));
    }

    @Test
    void shouldGenerateRequestIdWhenMissing() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter body = new StringWriter();
        when(request.getAttribute(RequestIdFilter.REQUEST_ID_ATTR)).thenReturn(null);
        when(response.getWriter()).thenReturn(new PrintWriter(body));

        handler.onAuthenticationFailure(request, response, new BadCredentialsException("fail"));

        verify(response).setStatus(401);
        String json = body.toString();
        assertFalse(json.contains("\"requestid\":null"));
    }
}
