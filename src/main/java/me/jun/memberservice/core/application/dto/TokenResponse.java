package me.jun.memberservice.core.application.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
public class TokenResponse {

    private String token;

    public static TokenResponse of(String token) {
        return TokenResponse.builder()
                .token(token)
                .build();
    }
}
