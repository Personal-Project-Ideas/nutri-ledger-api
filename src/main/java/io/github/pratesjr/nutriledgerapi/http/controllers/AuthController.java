package io.github.pratesjr.nutriledgerapi.http.controllers;

import io.github.pratesjr.nutriledgerapi.application.ports.CreateUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.application.ports.AuthUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import io.github.pratesjr.nutriledgerapi.application.ports.AuthCookieServicePort;
import io.github.pratesjr.nutriledgerapi.application.mappers.ResponseMapper;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import io.github.pratesjr.nutriledgerapi.infra.security.GoogleOAuthPrincipal;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final CreateUserUseCasePort createUserUseCase;
    private final ResponseMapper responseMapper;

    private final AuthUserUseCasePort authUserUseCase;
    private final AuthCookieServicePort authCookieService;

    @Autowired
    public AuthController(
            CreateUserUseCasePort createUserUseCase,
            ResponseMapper responseMapper,
            AuthUserUseCasePort authUserUseCase,
            AuthCookieServicePort authCookieService
    ) {
        this.createUserUseCase = createUserUseCase;
        this.responseMapper = responseMapper;
        this.authUserUseCase = authUserUseCase;
        this.authCookieService = authCookieService;
    }

    @Operation(
        summary = "Sign in an existing user",
        description = "Signs in a user with Google OAuth and sets the authentication cookie in the response. No response body is returned.",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "User signed in successfully. Auth cookie is set in the response.",
                headers = {
                    @Header(
                        name = "Set-Cookie",
                        description = "Authentication cookie (HTTP-only, Secure) containing the JWT token.",
                        schema = @Schema(type = "string")
                    )
                }
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized or invalid credentials."
            )
        }
    )
    @PostMapping("google/signin")
    ResponseEntity<Void> signIn(
            @AuthenticationPrincipal GoogleOAuthPrincipal principal,
            HttpServletResponse response
    ){
        String token = this.authUserUseCase.authenticate(principal.oauthUserDto()).getToken();
        this.authCookieService.addAuthCookie(response, token);

        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Sign up a new user",
        description = "Creates a new user if allowed and returns the user response. Also sets the authentication cookie in the response.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "User created successfully. Auth cookie is set in the response.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class)
                ),
                headers = {
                    @Header(
                        name = "Set-Cookie",
                        description = "Authentication cookie (HTTP-only, Secure) containing the JWT token.",
                        schema = @Schema(type = "string")
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid input"
            ),
            @ApiResponse(
                responseCode = "409",
                description = "User already exists or not allowed"
            )
        }
    )
    @PostMapping("google/signup")
    public ResponseEntity<UserResponseDto> signUp(
            @AuthenticationPrincipal GoogleOAuthPrincipal principal,
            HttpServletResponse response
    ) {
        OAuthUserDto oAuthUserDto = principal.oauthUserDto();
        String email = oAuthUserDto.getEmail();
        AllowedUser trustedAllowedUser = new AllowedUser(email);
        User user = createUserUseCase.process(trustedAllowedUser, oAuthUserDto);
        String token = this.authUserUseCase.authenticate(oAuthUserDto).getToken();
        this.authCookieService.addAuthCookie(response, token);
        UserResponseDto userResponse = responseMapper.toUserResponseDto(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
