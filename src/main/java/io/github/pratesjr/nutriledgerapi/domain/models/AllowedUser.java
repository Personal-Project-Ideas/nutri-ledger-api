package io.github.pratesjr.nutriledgerapi.domain.models;

import lombok.Getter;


@Getter
public class AllowedUser {

    private final String email;

    public  AllowedUser(String email) {
        this.email = email;
    }
}
