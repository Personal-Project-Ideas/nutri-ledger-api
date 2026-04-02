package io.github.pratesjr.nutriledgerapi.http.controllers;

import io.github.pratesjr.nutriledgerapi.application.mappers.AllowedUserModelMapper;
import io.github.pratesjr.nutriledgerapi.application.ports.CreateUserUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "User management endpoints")
@RestController
@RequestMapping("/user")
public class UserController {

    private final AllowedUserModelMapper allowedUserModelMapper;
    private final CreateUserUseCase createUserUseCase;

    public UserController(AllowedUserModelMapper allowedUserModelMapper,CreateUserUseCase createUserUseCase ) {
        this.allowedUserModelMapper = allowedUserModelMapper;
        this.createUserUseCase = createUserUseCase;
    }


@PostMapping()

}
