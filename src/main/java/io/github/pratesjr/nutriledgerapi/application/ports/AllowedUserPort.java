package io.github.pratesjr.nutriledgerapi.application.ports;

public interface AllowedUserPort {
    boolean isAllowed(String email);
}
