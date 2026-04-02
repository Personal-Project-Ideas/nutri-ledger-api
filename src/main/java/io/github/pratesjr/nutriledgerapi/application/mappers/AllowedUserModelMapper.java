package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.http.dtos.AllowedUserDto;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AllowedUserModelMapper  implements ModelMapper<AllowedUserDto, AllowedUser> {

    @Override
    public AllowedUser toModel(AllowedUserDto dto) {
        if (dto == null) {
            return null;
        }

        return new AllowedUser(dto.getEmail());
    }


}
