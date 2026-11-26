package me.tuhin47.jwt;

import me.tuhin47.config.AppProperties;
import me.tuhin47.config.exception.JwtTokenMalformedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenProviderTest {

    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        AppProperties appProperties = new AppProperties();
        appProperties.getAuth().setTokenSecret(Base64.getEncoder().encodeToString(new byte[64]));
        appProperties.getAuth().setTokenExpirationMsec(60000);
        tokenProvider = new TokenProvider(appProperties);
    }

    @Test
    void createsAndReadsSignedToken() {
        String token = tokenProvider.createToken(false, "user@example.com");

        assertTrue(tokenProvider.validateToken(token));
        assertEquals("user@example.com", tokenProvider.getUserIdFromToken(token));
        assertFalse(tokenProvider.isAuthenticated(token));
    }

    @Test
    void rejectsMalformedToken() {
        assertThrows(JwtTokenMalformedException.class, () -> tokenProvider.validateToken("not-a-jwt"));
    }
}