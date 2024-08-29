package me.jun.memberservice.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.memberservice.core.application.dto.RegisterRequest;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.Password;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;

import static java.time.Instant.now;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
abstract public class MemberFixture {

    public static final Long MEMBER_ID = 1L;

    public static final String NAME = "name string";

    public static final String EMAIL = "email string";

    public static final String PASSWORD = "password string";

    public static final String ADMIN = "ADMIN";

    public static final String USER = "USER";

    public static final GrantedAuthority ADMIN_GRANTED_AUTHORITY = new SimpleGrantedAuthority(ADMIN);

    public static final GrantedAuthority USER_GRANTED_AUTHORITY = new SimpleGrantedAuthority(USER);

    public static final Collection<GrantedAuthority> GRANTED_AUTHORITIES = Arrays.asList(
            ADMIN_GRANTED_AUTHORITY,
            USER_GRANTED_AUTHORITY
    );

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
                .authorities(GRANTED_AUTHORITIES)
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
                .authorities(GRANTED_AUTHORITIES)
                .password(password())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();
    }

    public static RegisterRequest registerRequest() {
        return RegisterRequest.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }
}
