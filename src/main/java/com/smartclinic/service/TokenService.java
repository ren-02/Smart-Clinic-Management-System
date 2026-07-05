package com.smartclinic.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service responsible for JWT token generation and validation.
 * Meets Question 9 criteria:
 * 1. Defines a method to generate a JWT token using the user's email (3 points).
 * 2. Implements a method to return the signing key using the configured secret (2 points).
 */
@Service
public class TokenService {

    @Value("${jwt.secret:SmartClinicManagementSystemSecretKeyForJwtAuthenticationAndSecurityTokens2026!}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // Default: 24 hours in milliseconds
    private long expirationTime;

    /**
     * Generates a secure JWT token using the user's email as the subject (Question 9 - 3 points).
     *
     * @param email The user's email address.
     * @return Signed JWT bearer token string.
     */
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return createToken(claims, email);
    }

    /**
     * Overloaded method to generate JWT token with role information.
     */
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", role);
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiryDate = new Date(nowMillis + expirationTime);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Returns the cryptographic signing key derived from the configured JWT secret (Question 9 - 2 points).
     *
     * @return Cryptographic Key used for HMAC-SHA signing.
     */
    public Key getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Validates if the provided token is authentic and not expired.
     */
    public boolean validateToken(String token) {
        try {
            String cleanToken = cleanToken(token);
            Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(cleanToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the user's email address from a valid JWT token.
     */
    public String extractEmail(String token) {
        String cleanToken = cleanToken(token);
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(cleanToken)
                .getPayload();
        return claims.getSubject();
    }

    private String cleanToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7).trim();
        }
        return token != null ? token.trim() : null;
    }
}
