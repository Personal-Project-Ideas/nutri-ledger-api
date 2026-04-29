package io.github.pratesjr.nutriledgerapi.domain.models;

import lombok.Getter;

@Getter
public class OAuthUser {

    private final String email;
    private final String fullName;
    private final String birthdate;

    public OAuthUser(String email, String fullName, String birthdate) {
        this.email = email;
        this.fullName = fullName;
        this.birthdate = birthdate;
    }
}
