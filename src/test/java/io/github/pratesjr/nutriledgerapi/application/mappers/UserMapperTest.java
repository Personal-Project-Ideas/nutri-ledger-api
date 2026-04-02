package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.domain.models.User;
import io.github.pratesjr.nutriledgerapi.http.dtos.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @Nested
    @DisplayName("toDto")
    class ToDto {

        @Test
        @DisplayName("should return null when model is null")
        void shouldReturnNullWhenModelIsNull() {
            UserResponseDto dto = mapper.toDto(null);

            assertNull(dto);
        }

        @Test
        @DisplayName("should map all fields from model to dto")
        void shouldMapAllFieldsFromModelToDto() {
            UUID id = UUID.randomUUID();
            Instant createdAt = Instant.now();
            Instant updatedAt = createdAt.plusSeconds(60);

            User model = new User(id, "John Doe", 30, createdAt, updatedAt);

            UserResponseDto dto = mapper.toDto(model);

            assertAll(
                    () -> assertEquals(id, dto.getId()),
                    () -> assertEquals("John Doe", dto.getFullName()),
                    () -> assertEquals(30, dto.getAge()),
                    () -> assertEquals(createdAt.toString(), dto.getCreatedAt()),
                    () -> assertEquals(updatedAt.toString(), dto.getUpdatedAt())
            );
        }

        @Test
        @DisplayName("should handle null dates")
        void shouldHandleNullDates() {
            UUID id = UUID.randomUUID();

            User model = new User(id, "John Doe", 30, null, null);

            UserResponseDto dto = mapper.toDto(model);

            assertAll(
                    () -> assertEquals(id, dto.getId()),
                    () -> assertNull(dto.getCreatedAt()),
                    () -> assertNull(dto.getUpdatedAt())
            );
        }
    }

    @Nested
    @DisplayName("toModel")
    class ToModel {

        @Test
        @DisplayName("should return null when dto is null")
        void shouldReturnNullWhenDtoIsNull() {
            User model = mapper.toModel(null);

            assertNull(model);
        }

        @Test
        @DisplayName("should map all fields from dto to model")
        void shouldMapAllFieldsFromDtoToModel() {
            UUID id = UUID.randomUUID();
            Instant createdAt = Instant.now();
            Instant updatedAt = createdAt.plusSeconds(60);

            UserResponseDto dto = new UserResponseDto();
            dto.setId(id);
            dto.setFullName("John Doe");
            dto.setAge(30);
            dto.setCreatedAt(createdAt.toString());
            dto.setUpdatedAt(updatedAt.toString());

            User model = mapper.toModel(dto);

            assertAll(
                    () -> assertEquals(id, model.getId()),
                    () -> assertEquals("John Doe", model.getFullName()),
                    () -> assertEquals(30, model.getAge()),
                    () -> assertEquals(createdAt, model.getCreatedAt()),
                    () -> assertEquals(updatedAt, model.getUpdatedAt())
            );
        }

        @Test
        @DisplayName("should handle null dates")
        void shouldHandleNullDates() {
            UUID id = UUID.randomUUID();

            UserResponseDto dto = new UserResponseDto();
            dto.setId(id);
            dto.setFullName("John Doe");
            dto.setAge(30);
            dto.setCreatedAt(null);
            dto.setUpdatedAt(null);

            User model = mapper.toModel(dto);

            assertAll(
                    () -> assertEquals(id, model.getId()),
                    () -> assertNull(model.getCreatedAt()),
                    () -> assertNull(model.getUpdatedAt())
            );
        }
    }
}

