package com.umer.contactmanagement.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String loginIdentifier) {

        Date currentDate = new Date();

        Date expirationDate = new Date(
                currentDate.getTime() + expiration);

        return Jwts.builder()
                .subject(loginIdentifier)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractLoginIdentifier(String token) {

        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String loginIdentifier) {

        try {
            String extractedIdentifier = extractLoginIdentifier(token);

            return extractedIdentifier.equals(loginIdentifier)
                    && extractAllClaims(token).getExpiration().after(new Date());

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
