package io.github.pratesjr.nutriledgerapi.http.controllers;

import io.github.pratesjr.nutriledgerapi.application.ports.CreateUserUseCase;
import io.github.pratesjr.nutriledgerapi.application.ports.AuthUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.application.mappers.ResponseMapper;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.UserResponseDto;
import io.github.pratesjr.nutriledgerapi.infra.services.AuthCookieServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final CreateUserUseCase createUserUseCase;
    private final ResponseMapper responseMapper;

    private final AuthUserUseCasePort authUserUseCase;
    private final AuthCookieServicePort authCookieService;

    @Autowired
    public AuthController(
            CreateUserUseCase createUserUseCase,
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
        summary = "Sign up a new user",
        description = "Creates a new user if allowed and returns the user response.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "User created successfully",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class)
                )
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
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(
            @RequestBody AllowedUser allowedUser,
            @AuthenticationPrincipal OAuth2User oAuth2User,
            HttpServletResponse response
    ) {
        User user = createUserUseCase.process(allowedUser);
        String token = this.authUserUseCase.authenticate(oAuth2User).getToken();
        this.authCookieService.addAuthCookie(response, token);
        UserResponseDto userResponse = responseMapper.toUserResponseDto(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
