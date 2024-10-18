package me.jun.memberservice.core.application.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class DeleteMemberRequest {

    private String email;

    public static DeleteMemberRequest of(String email) {
        return DeleteMemberRequest.builder()
                .email(email)
                .build();
    }
}
