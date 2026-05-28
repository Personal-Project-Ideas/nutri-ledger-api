package io.github.pratesjr.nutriledgerapi.infra.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PublicEndpointPathsTest {

    @Test
    void shouldTreatGoogleAuthRoutesAsPublic() {
        assertTrue(PublicEndpointPaths.isPublicPath("/auth/google/signin"));
        assertTrue(PublicEndpointPaths.isPublicPath("/auth/google/signup"));
        assertTrue(PublicEndpointPaths.isPublicPath("/auth/signout"));
    }

    @Test
    void shouldTreatProtectedBusinessRoutesAsNonPublic() {
        assertFalse(PublicEndpointPaths.isPublicPath("/portions"));
    }
}
