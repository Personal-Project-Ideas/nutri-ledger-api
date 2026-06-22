package io.github.pratesjr.nutriledgerapi.infra.persistences;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import io.github.pratesjr.nutriledgerapi.application.mappers.AllowedUserEntityMapperImpl;
import io.github.pratesjr.nutriledgerapi.application.services.AllowedUserService;
import io.github.pratesjr.nutriledgerapi.domain.models.AllowedUser;
import io.github.pratesjr.nutriledgerapi.infra.entities.AllowedUserEntity;
import io.github.pratesjr.nutriledgerapi.infra.persistences.support.PersistenceIntegrationTest;
import io.github.pratesjr.nutriledgerapi.infra.repositories.AllowedUserRepository;

@PersistenceIntegrationTest
@Import({AllowedUserPersistence.class, AllowedUserEntityMapperImpl.class, AllowedUserService.class})
class AllowedUserPersistenceTest {

    private static final String ALLOWED_EMAIL = "allowed@example.com";
    private static final String UNKNOWN_EMAIL = "not-on-allowlist@example.com";

    @Autowired
    private AllowedUserPersistence persistence;

    @Autowired
    private AllowedUserService allowedUserService;

    @Autowired
    private AllowedUserRepository repository;

    @Test
    void shouldNotBeAllowedWhenEmailIsNotOnAllowList() {
        assertNull(persistence.findByEmail(UNKNOWN_EMAIL));
        assertFalse(allowedUserService.isAllowed(UNKNOWN_EMAIL));
    }

    @Test
    void shouldBeAllowedAfterInsertingUserOnAllowList() {
        insertAllowedUser(ALLOWED_EMAIL);

        assertTrue(allowedUserService.isAllowed(ALLOWED_EMAIL));

        AllowedUser found = persistence.findByEmail(ALLOWED_EMAIL);
        assertEquals(ALLOWED_EMAIL, found.getEmail());
    }

    private void insertAllowedUser(String email) {
        repository.save(new AllowedUserEntity(null, email));
    }
}
