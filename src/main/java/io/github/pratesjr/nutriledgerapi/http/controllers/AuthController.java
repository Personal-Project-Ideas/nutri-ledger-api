package io.github.pratesjr.nutriledgerapi.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.pratesjr.nutriledgerapi.application.ports.AuthCookieServicePort;
import io.github.pratesjr.nutriledgerapi.application.ports.AuthUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.infra.security.GoogleOAuthPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Tag(name = "Authentication", description = "Endpoints for user authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserUseCasePort authUserUseCase;
    private final AuthCookieServicePort authCookieService;

    @Autowired
    public AuthController(
            AuthUserUseCasePort authUserUseCase,
            AuthCookieServicePort authCookieService
    ) {
        this.authUserUseCase = authUserUseCase;
        this.authCookieService = authCookieService;
    }

    @Operation(
        summary = "Start Google sign-in",
        description = "Redirects to Google OAuth. On success, sets AUTH_TOKEN cookie and returns 200."
    )
    @GetMapping("google/signin")
    public void startGoogleSignIn(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        response.sendRedirect(request.getContextPath() + "/oauth2/authorization/google");
    }

    @Operation(
        summary = "Sign in an existing user",
        description = "Issues JWT cookie for an active Google OAuth session.",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Signed in. Auth cookie set.",
                headers = {
                    @Header(
                        name = "Set-Cookie",
                        description = "HTTP-only JWT cookie.",
                        schema = @Schema(type = "string")
                    )
                }
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")
        }
    )
    @PostMapping("google/signin")
    public ResponseEntity<Void> signIn(
            @AuthenticationPrincipal GoogleOAuthPrincipal principal,
            HttpServletResponse response
    ) {
        String token = this.authUserUseCase.authenticate(principal.oauthUserDto()).token();
        this.authCookieService.addAuthCookie(response, token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("signout")
    public ResponseEntity<Void> signOut(HttpServletResponse response) {
        this.authCookieService.removeAuthCookie(response);
        return ResponseEntity.noContent().build();
    }
}
