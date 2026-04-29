package io.github.pratesjr.nutriledgerapi.application.usecases;

import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import io.github.pratesjr.nutriledgerapi.application.mappers.OAuthUserMapper;
import io.github.pratesjr.nutriledgerapi.application.ports.AllowedUserPort;
import io.github.pratesjr.nutriledgerapi.application.ports.CreateUserUseCasePort;
import io.github.pratesjr.nutriledgerapi.application.ports.UserPersistencePort;
import io.github.pratesjr.nutriledgerapi.domain.errors.UserNotAllowedToBeCreatedException;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.domain.models.OAuthUser;
import io.github.pratesjr.nutriledgerapi.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

@Service
public class CreateUserUseCase  implements CreateUserUseCasePort {
    private final AllowedUserPort allowedUserService;
    private final UserPersistencePort userPersistence;
    private final OAuthUserMapper oAuthUserMapper;

    @Autowired
    public CreateUserUseCase(
            AllowedUserPort allowedUserService,
            UserPersistencePort userPersistence,
            OAuthUserMapper oAuthUserMapper) {
        this.allowedUserService = allowedUserService;
        this.userPersistence = userPersistence;
        this.oAuthUserMapper = oAuthUserMapper;
    }

    @Override
    public User process(AllowedUser user, OAuthUserDto oAuthInfo) {
        if(!this.allowedUserService.isAllowed(user.getEmail())){
            throw new UserNotAllowedToBeCreatedException(user.getEmail());
        }
        User existing = userPersistence.findByEmail(user.getEmail());
        if (existing != null) {
            return existing;
        }

        OAuthUser oauthUser = oAuthUserMapper.toDomain(oAuthInfo);
        String email = oauthUser.getEmail();
        String fullName = oauthUser.getFullName();
        Integer age = null;
        String birthdateStr = oauthUser.getBirthdate();
        if (birthdateStr != null) {
            try {
                LocalDate birthdate = LocalDate.parse(birthdateStr);
                age = Period.between(birthdate, LocalDate.now()).getYears();
            } catch (DateTimeParseException e) {
                // Ignore invalid format, keep age as null
            }
        }
        User newUser = new User(fullName, email, age);
        return userPersistence.create(newUser);
    }

}
