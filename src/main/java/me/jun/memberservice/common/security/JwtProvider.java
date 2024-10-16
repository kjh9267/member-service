package me.jun.memberservice.common.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MINUTES;

@Component
@SuppressWarnings("deprecation")
public class JwtProvider {

    private final String jwtKey;

    JwtProvider(@Value("#{${jwt-key}}") String jwtKey) {
        this.jwtKey = jwtKey;
    }

    public String createToken(Long id) {
        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(Date.from(now()))
                .expiration(Date.from(now().plus(30, MINUTES)))
                .signWith(HS512, jwtKey)
                .compact();
    }
}
