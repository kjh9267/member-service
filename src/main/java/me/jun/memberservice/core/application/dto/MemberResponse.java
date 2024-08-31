package me.jun.memberservice.core.application.dto;

import lombok.*;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.Role;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MemberResponse {

    private Long id;

    private String email;

    private String name;

    private Role role;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}
