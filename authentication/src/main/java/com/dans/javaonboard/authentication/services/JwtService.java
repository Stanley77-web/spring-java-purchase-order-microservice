package com.dans.javaonboard.authentication.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final String SECRET_KEY = System.getenv("SECRET_KEY");
    private final long EXPIRATION_TIME = System.getenv("EXPIRATION_TIME") != null ? Long.parseLong(System.getenv("EXPIRATION_TIME")) : 3600000;

    public String createToken(String username) {

        byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        long now = System.currentTimeMillis();

        JwtBuilder jwtBuilder = Jwts.builder()
                .id(String.valueOf(Math.random()))
                .issuedAt(new Date(now))
                .issuer(username)
                .expiration(new Date(now + EXPIRATION_TIME))
                .claim("username", username)
                .signWith(secretKey);

        return jwtBuilder.compact();
    }

    public Claims verifyToken(String token) {
        byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
