package me.jun.memberservice.core.domain;

import me.jun.memberservice.core.domain.exception.PasswordMismatchException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.memberservice.support.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
public class MemberTest {

    @Test
    void constructorTest() {
        Member expected = new Member();

        assertThat(new Member())
                .isEqualTo(expected);
    }

    @Test
    void constructorTest2() {
        Member expected = Member.builder()
                .id(MEMBER_ID)
                .name(NAME)
                .email(EMAIL)
                .role(Role.USER)
                .password(password())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();

        assertThat(user())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void validatePasswordTest() {
        assertThrows(
                PasswordMismatchException.class,
                () -> admin().validatePassword("wrong password string")
        );
    }
}
