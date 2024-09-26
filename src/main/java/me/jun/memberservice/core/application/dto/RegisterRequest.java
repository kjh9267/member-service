package me.jun.memberservice.core.application.dto;

import lombok.*;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.Password;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static me.jun.memberservice.core.domain.Role.USER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class RegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(Password.of(password))
                .role(USER)
                .build();
    }
}
