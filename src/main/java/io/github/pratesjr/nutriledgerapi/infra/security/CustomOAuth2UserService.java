package io.github.pratesjr.nutriledgerapi.infra.security;

import io.github.pratesjr.nutriledgerapi.application.mappers.OAuthUserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.core.ParameterizedTypeReference;

import java.util.*;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final String peopleApiUrl;
    private final OAuthUserMapper oAuthUserMapper;

    public CustomOAuth2UserService(
            @Value("${GOOGLE_PEOPLE_API_URL}") String peopleApiUrl,
            OAuthUserMapper oAuthUserMapper) {
        this.peopleApiUrl = peopleApiUrl;
        this.oAuthUserMapper = oAuthUserMapper;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if ("google".equals(registrationId)) {
            String accessToken = userRequest.getAccessToken().getTokenValue();
            try {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(accessToken);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                        peopleApiUrl,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<Map<String, Object>>() {}
                );
                Map<String, Object> body = response.getBody();
                if (body != null && body.containsKey("birthdays")) {
                    Object birthdaysObj = body.get("birthdays");
                    if (birthdaysObj instanceof List<?> birthdaysList) {
                        List<Map<String, Object>> birthdays = new ArrayList<>();
                        for (Object b : birthdaysList) {
                            if (b instanceof Map<?, ?>) {
                                @SuppressWarnings("unchecked")
                                Map<String, Object> bMap = ((Map<?, ?>) b).entrySet().stream()
                                    .collect(java.util.stream.Collectors.toMap(
                                        e -> e.getKey().toString(),
                                        Map.Entry::getValue
                                    ));
                                birthdays.add(bMap);
                            }
                        }
                        if (!birthdays.isEmpty()) {
                            Map<String, Object> birthday = birthdays.stream()
                                .filter(b -> {
                                    Object metaObj = b.get("metadata");
                                    if (metaObj instanceof Map<?, ?>) {
                                        Map<String, Object> metadata = ((Map<?, ?>) metaObj).entrySet().stream()
                                            .collect(java.util.stream.Collectors.toMap(
                                                e -> e.getKey().toString(),
                                                Map.Entry::getValue
                                            ));
                                        return Boolean.TRUE.equals(metadata.get("primary"));
                                    }
                                    return false;
                                })
                                .findFirst()
                                .orElse(birthdays.getFirst());
                            Object dateObj = birthday.get("date");
                            if (dateObj instanceof Map<?, ?>) {
                                Map<String, Object> date = ((Map<?, ?>) dateObj).entrySet().stream()
                                    .collect(java.util.stream.Collectors.toMap(
                                        e -> e.getKey().toString(),
                                        Map.Entry::getValue
                                    ));
                                int year = 0, month = 0, day = 0;
                                Object yearObj = date.get("year");
                                Object monthObj = date.get("month");
                                Object dayObj = date.get("day");
                                if (yearObj instanceof Number) year = ((Number) yearObj).intValue();
                                if (monthObj instanceof Number) month = ((Number) monthObj).intValue();
                                if (dayObj instanceof Number) day = ((Number) dayObj).intValue();
                                String birthdate;
                                if (year > 0) {
                                    birthdate = String.format("%04d-%02d-%02d", year, month, day);
                                } else {
                                    birthdate = String.format("%02d-%02d", month, day);
                                }
                                attributes.put("birthdate", birthdate);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // Optionally log error, but do not fail login
            }
        }
        OAuth2User enriched = new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                attributes,
                "sub"
        );
        return new GoogleOAuthPrincipal(enriched, oAuthUserMapper.toDtoFromOAuthAttributes(attributes));
    }
}
