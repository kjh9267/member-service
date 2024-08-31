package me.jun.memberservice.core.application;

import me.jun.memberservice.core.application.dto.MemberResponse;
import me.jun.memberservice.core.application.exception.DuplicatedEmailException;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.core.publisher.Mono;

import static me.jun.memberservice.support.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
class RegisterServiceTest {

    private RegisterService registerService;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        registerService = new RegisterService(memberRepository);
    }

    @Test
    void registerTest() {
        MemberResponse expected = memberResponse();

        given(memberRepository.save(any()))
                .willReturn(user());

        assertThat(registerService.register(Mono.just(registerRequest())).block())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void duplicatedEmail_registerFailTest() {
        given(memberRepository.save(any()))
                .willThrow(DataIntegrityViolationException.class);

        assertThrows(
                DuplicatedEmailException.class,
                () -> registerService.register(Mono.just(registerRequest())).block()
        );
    }
}