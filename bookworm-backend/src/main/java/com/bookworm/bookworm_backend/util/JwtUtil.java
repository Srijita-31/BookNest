package com.bookworm.bookworm_backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for JSON Web Token (JWT) operations.
 * This class handles the creation, validation, and parsing of JWTs.
 */
@Component
public class JwtUtil {

    // A secret key for signing the JWT. In a real application, this should be a secret from a configuration file.
    private static final String SECRET = "f4b6a9c3d2e1f5a8b7c4d2e1f5a8b7c4d2e1f5a8b7c4d2e1f5a8b7c4d2e1f5a8";

    // Method to extract the username from a JWT.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Method to check if the token has expired.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // A generic method to extract a specific claim from the token.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Method to extract all claims (payload) from the token.
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Method to check if the token is valid.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Method to validate a token.
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Method to generate a token with a subject (e.g., username).
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Helper method to create the token with a 24-hour expiration time.
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours validity
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Helper method to get the signing key from the secret.
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        return false;
    }
}
