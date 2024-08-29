package me.jun.memberservice.core.domain;

import me.jun.memberservice.core.domain.exception.PasswordMismatchException;
import org.junit.jupiter.api.Test;

import static me.jun.memberservice.support.MemberFixture.PASSWORD;
import static me.jun.memberservice.support.MemberFixture.password;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("deprecation")
public class PasswordTest {

    @Test
    void constructorTest() {
        Password expected = new Password();

        assertThat(new Password())
                .isEqualTo(expected);
    }

    @Test
    void constructorTest2() {
        Password expected = Password.builder()
                .value(PASSWORD)
                .build();

        assertThat(password())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void validateTest() {
        assertThrows(
                PasswordMismatchException.class,
                () -> password().validate("wrong password string")
        );
    }
}
