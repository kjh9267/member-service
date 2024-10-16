package me.jun.memberservice.common.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static me.jun.memberservice.support.MemberFixture.MEMBER_ID;
import static me.jun.memberservice.support.TokenFixture.JWT_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JwtProviderTest {

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(JWT_KEY);
    }

    @Test
    void createTokenTest() {
        String token = jwtProvider.createToken(MEMBER_ID);

        assertThat(token.split("\\.").length)
                .isEqualTo(3);
    }
}