package io.github.pratesjr.nutriledgerapi.application.mappers;

import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import io.github.pratesjr.nutriledgerapi.domain.models.OAuthUser;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OAuthUserMapper {

    public OAuthUserDto toDtoFromOAuthAttributes(Map<String, ?> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return new OAuthUserDto(null, null, null);
        }
        return new OAuthUserDto(
                stringAttr(attributes, "email"),
                stringAttr(attributes, "name"),
                stringAttr(attributes, "birthdate"));
    }

    public OAuthUser toDomain(OAuthUserDto dto) {
        if (dto == null) {
            return new OAuthUser(null, null, null);
        }
        return new OAuthUser(dto.email(), dto.fullName(), dto.birthdate());
    }

    public OAuthUserDto toDto(OAuthUser domain) {
        if (domain == null) {
            return new OAuthUserDto(null, null, null);
        }
        return new OAuthUserDto(domain.getEmail(), domain.getFullName(), domain.getBirthdate());
    }

    private static String stringAttr(Map<String, ?> attributes, String key) {
        Object v = attributes.get(key);
        return v instanceof String s ? s : v != null ? String.valueOf(v) : null;
    }
}
