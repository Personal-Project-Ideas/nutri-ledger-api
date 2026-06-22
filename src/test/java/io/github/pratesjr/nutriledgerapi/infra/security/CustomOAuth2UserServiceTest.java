package io.github.pratesjr.nutriledgerapi.infra.security;

import io.github.pratesjr.nutriledgerapi.application.mappers.OAuthUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomOAuth2UserServiceTest {

    private final OAuthUserMapper mapper = new OAuthUserMapper();

    @Test
    void shouldReturnGoogleOAuthPrincipalWrappingDelegateUser() {
        Map<String, Object> attrs = Map.of("sub", "123", "email", "u@test.com", "name", "Test User");

        CustomOAuth2UserService service = new CustomOAuth2UserService("http://people-api", mapper) {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest request) {
                DefaultOAuth2User delegate = new DefaultOAuth2User(AuthorityUtils.NO_AUTHORITIES, attrs, "sub");
                return new GoogleOAuthPrincipal(delegate, mapper.toDtoFromOAuthAttributes(attrs));
            }
        };

        OAuth2User result = service.loadUser(null);

        assertInstanceOf(GoogleOAuthPrincipal.class, result);
        assertEquals("u@test.com", ((GoogleOAuthPrincipal) result).oauthUserDto().email());
    }

    @Test
    void shouldMapBirthdateAttributeIntoPrincipal() {
        Map<String, Object> attrs = Map.of("sub", "123", "email", "u@test.com", "name", "Test User", "birthdate", "1990-05-20");

        CustomOAuth2UserService service = new CustomOAuth2UserService("http://people-api", mapper) {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest request) {
                DefaultOAuth2User delegate = new DefaultOAuth2User(AuthorityUtils.NO_AUTHORITIES, attrs, "sub");
                return new GoogleOAuthPrincipal(delegate, mapper.toDtoFromOAuthAttributes(attrs));
            }
        };

        OAuth2User result = service.loadUser(null);

        assertEquals("1990-05-20", ((GoogleOAuthPrincipal) result).oauthUserDto().birthdate());
    }

    @Test
    void shouldReturnPrincipalWithNullBirthdateWhenAttributeMissing() {
        Map<String, Object> attrs = Map.of("sub", "123", "email", "u@test.com", "name", "Test User");

        CustomOAuth2UserService service = new CustomOAuth2UserService("http://people-api", mapper) {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest request) {
                DefaultOAuth2User delegate = new DefaultOAuth2User(AuthorityUtils.NO_AUTHORITIES, attrs, "sub");
                return new GoogleOAuthPrincipal(delegate, mapper.toDtoFromOAuthAttributes(attrs));
            }
        };

        OAuth2User result = service.loadUser(null);

        assertNull(((GoogleOAuthPrincipal) result).oauthUserDto().birthdate());
    }

    @Test
    void shouldMapEmailAndNameFromAttributes() {
        Map<String, Object> attrs = Map.of("sub", "456", "email", "other@test.com", "name", "Other User");

        CustomOAuth2UserService service = new CustomOAuth2UserService("http://people-api", mapper) {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest request) {
                DefaultOAuth2User delegate = new DefaultOAuth2User(AuthorityUtils.NO_AUTHORITIES, attrs, "sub");
                return new GoogleOAuthPrincipal(delegate, mapper.toDtoFromOAuthAttributes(attrs));
            }
        };

        GoogleOAuthPrincipal result = (GoogleOAuthPrincipal) service.loadUser(null);

        assertEquals("other@test.com", result.oauthUserDto().email());
        assertEquals("Other User", result.oauthUserDto().fullName());
    }
}
