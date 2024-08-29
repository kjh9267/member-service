package me.jun.memberservice.core.application.dto;

import lombok.*;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.Password;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class RegisterRequest {

    private String name;

    private String email;

    private String password;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(
                        Password.builder()
                                .value(password)
                                .build()
                )
                .build();
    }
}
