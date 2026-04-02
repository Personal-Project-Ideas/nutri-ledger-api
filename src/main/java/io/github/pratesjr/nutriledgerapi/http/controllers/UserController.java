package io.github.pratesjr.nutriledgerapi.http.controllers;

import io.github.pratesjr.nutriledgerapi.application.mappers.AllowedUserMapper;
import io.github.pratesjr.nutriledgerapi.application.mappers.ResponseMapper;
import io.github.pratesjr.nutriledgerapi.application.ports.CreateUserUseCase;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.AllowedUserDto;
import io.github.pratesjr.nutriledgerapi.http.dtos.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "User management endpoints")
@RestController
@RequestMapping("/user")
public class UserController {

    private final AllowedUserMapper allowedUserMapper;
    private final ResponseMapper responseMapper;
    private final CreateUserUseCase createUserUseCase;

    public UserController(AllowedUserMapper allowedUserMapper,
                          CreateUserUseCase createUserUseCase,
                          ResponseMapper responseMapper) {
        this.allowedUserMapper = allowedUserMapper;
        this.createUserUseCase = createUserUseCase;
        this.responseMapper = responseMapper;
    }

    @Operation(
            summary = "Create a new user",
            description = "Registers a new user based on the provided email",
            responses = {
                @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully"
                ),
                 @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
                ),
                 @ApiResponse(
                    responseCode = "409",
                    description = "User with the given email already exists"
                )
            }
    )
    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody AllowedUserDto allowedUser) {
        AllowedUser userEmail = allowedUserMapper.toModel(allowedUser);
        User createdUser = createUserUseCase.process(userEmail);
        UserResponseDto response = responseMapper.toUserResponseDto(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
