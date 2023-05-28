package com.example.usermicroservice.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.example.usermicroservice.model.JwtAuthentication;
import com.example.usermicroservice.model.Role;
import com.example.usermicroservice.model.User;

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

    public String generateAccessToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Date exp = Date.from(now.plusHours(1).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                   .setSubject(user.getEmail())
                   .setExpiration(exp)
                   .signWith(secretKey)
                   .claim("roles", List.of(user.getRole()))
                   .claim("id", user.getId())
                   .compact();
    }

    public Claims getClaims(String token) {
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
        jwtAuthentication.setUsername(claims.get("name", String.class));
        jwtAuthentication.setRoles(((List<String>) claims.get("roles", List.class))
                                                         .stream()
                                                         .map(Role::valueOf)
                                                         .collect(Collectors.toSet()));
        return jwtAuthentication;
    }
}
