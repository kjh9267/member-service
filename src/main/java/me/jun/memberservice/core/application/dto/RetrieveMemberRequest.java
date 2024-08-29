package me.jun.memberservice.core.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.Password;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class RetrieveMemberRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(Password.of(password))
                .build();
    }
}
