package me.jun.memberservice.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.Password;
import me.jun.memberservice.core.domain.Role;

import java.time.Instant;

import static java.time.Instant.now;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
abstract public class MemberFixture {

    public static final Long MEMBER_ID = 1L;

    public static final String NAME = "name string";

    public static final String EMAIL = "email string";

    public static final String PASSWORD = "password string";

    public static final Instant CREATED_AT = now();

    public static final Instant UPDATED_AT = now();

    public static Password password() {
        return Password.builder()
                .value(PASSWORD)
                .build();
    }

    public static Member admin() {
        return Member.builder()
                .id(MEMBER_ID)
                .name(NAME)
                .email(EMAIL)
                .role(Role.ADMIN)
                .password(password())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();
    }

    public static Member user() {
        return Member.builder()
                .id(MEMBER_ID)
                .name(NAME)
                .email(EMAIL)
                .role(Role.USER)
                .password(password())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();
    }
}