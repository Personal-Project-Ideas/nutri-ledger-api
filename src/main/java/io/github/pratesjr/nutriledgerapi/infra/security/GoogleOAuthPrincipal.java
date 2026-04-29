package io.github.pratesjr.nutriledgerapi.infra.security;

import io.github.pratesjr.nutriledgerapi.application.dtos.OAuthUserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Wraps Spring's {@link OAuth2User} and carries an application {@link OAuthUserDto}
 * so controllers and use cases do not depend on framework types.
 */
public final class GoogleOAuthPrincipal implements OAuth2User {

    private final OAuth2User delegate;
    private final OAuthUserDto oauthUserDto;

    public GoogleOAuthPrincipal(OAuth2User delegate, OAuthUserDto oauthUserDto) {
        this.delegate = Objects.requireNonNull(delegate);
        this.oauthUserDto = Objects.requireNonNull(oauthUserDto);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return delegate.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return delegate.getAuthorities();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    public OAuthUserDto oauthUserDto() {
        return oauthUserDto;
    }
}
