package me.jun.memberservice.core.application;

import me.jun.memberservice.common.security.JwtProvider;
import me.jun.memberservice.core.application.dto.TokenResponse;
import me.jun.memberservice.core.application.exception.MemberNotFoundException;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.Password;
import me.jun.memberservice.core.domain.exception.PasswordMismatchException;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static me.jun.memberservice.support.MemberFixture.loginRequest;
import static me.jun.memberservice.support.MemberFixture.user;
import static me.jun.memberservice.support.TokenFixture.TOKEN;
import static me.jun.memberservice.support.TokenFixture.tokenResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
public class LoginServiceTest {

    private LoginService loginService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private Member member;

    @BeforeEach
    void setUp() {
        loginService = new LoginService(memberRepository, jwtProvider);
    }

    @Test
    void loginTest() {
        TokenResponse expected = tokenResponse();

        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(user()));

        given(jwtProvider.createToken(any()))
                .willReturn(TOKEN);

        assertThat(loginService.login(Mono.just(loginRequest())).block())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void noEmail_loginFailTest() {
        given(memberRepository.findByEmail(any()))
                .willThrow(MemberNotFoundException.class);

        assertThrows(
                MemberNotFoundException.class,
                () -> loginService.login(Mono.just(loginRequest())).block()
        );
    }

    @Test
    void wrongPassword_loginFailTest() {
        Password wrongPassword = Password.of("wrong password");

        Member wrongUser = user().toBuilder()
                .password(wrongPassword)
                .build();

        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(wrongUser));

        assertThrows(
                PasswordMismatchException.class,
                () -> loginService.login(Mono.just(loginRequest())).block()
        );
    }
}
