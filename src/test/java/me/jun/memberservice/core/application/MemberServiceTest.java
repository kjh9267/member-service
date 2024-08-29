package me.jun.memberservice.core.application;

import me.jun.memberservice.core.application.exception.MemberNotFoundException;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static me.jun.memberservice.support.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void loadUserByUsernameTest() {
        UserDetails expected = Member.builder()
                .id(MEMBER_ID)
                .name(NAME)
                .email(EMAIL)
                .authorities(GRANTED_AUTHORITIES)
                .password(password())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();

        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(user()));

        assertThat(memberService.loadUserByUsername(EMAIL))
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    void loadUserByUsernameFailTest() {
        given(memberRepository.findByEmail(any()))
                .willThrow(MemberNotFoundException.class);

        assertThrows(
                MemberNotFoundException.class,
                () -> memberService.loadUserByUsername(EMAIL)
        );
    }
}