package com.satya.ecom.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final SecretKey key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().stream().findFirst().get());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
