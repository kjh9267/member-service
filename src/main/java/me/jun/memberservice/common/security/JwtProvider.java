package me.jun.memberservice.common.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
public class JwtProvider {

    private final String jwtKey;

    JwtProvider(@Value("#{${jwt-key}}") String jwtKey) {
        this.jwtKey = jwtKey;
    }

    public String createToken(Long id) {
        return Jwts.builder()
                .subject(id.toString())
                .signWith(HS512, jwtKey)
                .compact();
    }
}
