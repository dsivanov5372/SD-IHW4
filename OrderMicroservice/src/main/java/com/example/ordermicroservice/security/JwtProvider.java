package com.example.ordermicroservice.security;

import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.example.ordermicroservice.model.JwtAuthentication;
import com.example.ordermicroservice.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@PropertySource("classpath:application.properties")
public class JwtProvider {
    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(secretKey)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public JwtAuthentication getJwtAuthentication(String token) {
        Claims claims = getClaims(token);
 
        final JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setEmail(claims.get("email", String.class));
        jwtAuthentication.setUsername(claims.get("name", String.class));
        jwtAuthentication.setRoles(((List<String>) claims.get("roles", List.class))
                                                         .stream()
                                                         .map(Role::valueOf)
                                                         .collect(Collectors.toSet()));
        return jwtAuthentication;
    }
}
