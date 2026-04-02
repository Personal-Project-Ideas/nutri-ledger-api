package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.http.dtos.AllowedUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AllowedUserMapperTest {

    private AllowedUserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AllowedUserMapper.class);
    }

    @Nested
    @DisplayName("toModel")
    class ToModel {

        @Test
        @DisplayName("should return null when dto is null")
        void shouldReturnNullWhenDtoIsNull() {
            AllowedUser model = mapper.toModel(null);

            assertNull(model);
        }

        @Test
        @DisplayName("should map email from dto to model")
        void shouldMapEmailFromDtoToModel() {
            AllowedUserDto dto = new AllowedUserDto();
            dto.setEmail("user@example.com");

            AllowedUser model = mapper.toModel(dto);

            assertEquals("user@example.com", model.getEmail());
        }
    }

    @Nested
    @DisplayName("toDto")
    class ToDto {

        @Test
        @DisplayName("should return null when model is null")
        void shouldReturnNullWhenModelIsNull() {
            AllowedUserDto dto = mapper.toDto(null);

            assertNull(dto);
        }

        @Test
        @DisplayName("should map email from model to dto")
        void shouldMapEmailFromModelToDto() {
            AllowedUser model = new AllowedUser("admin@example.com");

            AllowedUserDto dto = mapper.toDto(model);

            assertEquals("admin@example.com", dto.getEmail());
        }
    }
}

