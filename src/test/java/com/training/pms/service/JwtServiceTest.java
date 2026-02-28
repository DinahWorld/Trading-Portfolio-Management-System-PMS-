package com.training.pms.service;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private static final String BASE64_SECRET = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";

    @Test
    void should_generate_token_and_extract_username() {
        JwtService jwtService = new JwtService(BASE64_SECRET, 60_000, 5);

        String token = jwtService.generateToken("alice");

        assertThat(token).isNotBlank();
        assertThat(jwtService.extractUsername(token)).isEqualTo("alice");
        assertThat(jwtService.isTokenExpired(token)).isFalse();
    }

    @Test
    void should_generate_token_with_extra_claims() {
        JwtService jwtService = new JwtService(BASE64_SECRET, 60_000, 5);

        String token = jwtService.generateToken(Map.of("role", "ADMIN"), "bob");

        String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));
        assertThat(role).isEqualTo("ADMIN");
        assertThat(jwtService.extractUsername(token)).isEqualTo("bob");
    }

    @Test
    void should_validate_token_for_expected_username() {
        JwtService jwtService = new JwtService(BASE64_SECRET, 60_000, 5);
        String token = jwtService.generateToken("charlie");

        assertThat(jwtService.isTokenValid(token, "charlie")).isTrue();
        assertThat(jwtService.isTokenValid(token, "david")).isFalse();
    }

    @Test
    void should_mark_token_as_expired_when_expiration_is_in_the_past() {
        JwtService expiredJwtService = new JwtService(BASE64_SECRET, -1_000, 0);
        String expiredToken = expiredJwtService.generateToken("eve");

        assertThat(expiredJwtService.isTokenExpired(expiredToken)).isTrue();
        assertThat(expiredJwtService.isTokenValid(expiredToken, "eve")).isFalse();
    }
}
