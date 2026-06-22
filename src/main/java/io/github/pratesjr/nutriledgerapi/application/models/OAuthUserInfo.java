package io.github.pratesjr.nutriledgerapi.application.models;

import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthUserInfo {

    private final String email;
    private final String fullName;
    private final String birthdate;

    public OAuthUserInfo(String email, String fullName, String birthdate) {
        this.email = email;
        this.fullName = fullName;
        this.birthdate = birthdate;
    }

    public static OAuthUserInfo fromAttributes(Map<String, ?> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return new OAuthUserInfo(null, null, null);
        }
        return new OAuthUserInfo(
                stringAttr(attributes, "email"),
                stringAttr(attributes, "name"),
                stringAttr(attributes, "birthdate"));
    }

    private static String stringAttr(Map<String, ?> attributes, String key) {
        Object v = attributes.get(key);
        return v instanceof String s ? s : v != null ? String.valueOf(v) : null;
    }
}
