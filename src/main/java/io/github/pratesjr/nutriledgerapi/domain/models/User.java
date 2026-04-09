package io.github.pratesjr.nutriledgerapi.domain.models;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class User {
    private final UUID id;
    private final String fullName;
    private final String email;
    private final Integer age;
    private final Instant createdAt;
    private final Instant updatedAt;


    public User(final UUID id, final String fullName, final String email, final Integer age, final Instant createdAt, final Instant updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(final String fullName, final String email, final Integer age) {
        this(null, fullName, email, age, null, null);
    }

}
