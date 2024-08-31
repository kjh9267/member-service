package me.jun.memberservice.core.application;

import me.jun.memberservice.core.application.dto.MemberResponse;
import me.jun.memberservice.core.application.exception.MemberNotFoundException;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.Role;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static me.jun.memberservice.support.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
class MemberServiceTest {

    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    void retrieveMemberTest() {
        MemberResponse expected = MemberResponse.builder()
                .id(MEMBER_ID)
                .name(NAME)
                .email(EMAIL)
                .role(Role.USER)
                .build();

        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(user()));

        assertThat(memberService.retrieveMember(Mono.just(retrieveMemberRequest())).block())
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void retrieveMemberFailTest() {
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.empty());

        assertThrows(
                MemberNotFoundException.class,
                () -> memberService.retrieveMember(Mono.just(retrieveMemberRequest())).block()
        );
    }
}