package me.jun.memberservice.core.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RoleTest {

    @Test
    void adminTest() {
        Role expected = Role.ADMIN;

        assertThat(Role.values()[0])
                .isEqualTo(expected);
    }

    @Test
    void userTest() {
        Role expected = Role.USER;

        assertThat(Role.values()[1])
                .isEqualTo(expected);
    }
}