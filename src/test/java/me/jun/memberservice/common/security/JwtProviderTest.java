package me.jun.memberservice.common.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static me.jun.memberservice.support.MemberFixture.EMAIL;
import static me.jun.memberservice.support.TokenFixture.JWT_KEY;
import static me.jun.memberservice.support.TokenFixture.TOKEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JwtProviderTest {

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(JWT_KEY);
    }

    @Test
    void createTokenTest() {
        assertThat(jwtProvider.createToken(EMAIL))
                .isEqualTo(TOKEN);
    }
}