package me.jun.memberservice.core.application;

import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static me.jun.memberservice.support.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
        UserDetails expected = Member.builder()
                .id(MEMBER_ID)
                .name(NAME)
                .email(EMAIL)
                .authorities(GRANTED_AUTHORITIES)
                .password(password())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();

        given(memberRepository.save(any()))
                .willReturn(user());

        assertThat(registerService.register(registerRequest()))
                .isEqualToComparingFieldByField(expected);
    }
}