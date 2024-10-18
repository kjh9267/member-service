package me.jun.memberservice.core.application;

import me.jun.memberservice.core.application.exception.MemberNotFoundException;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static me.jun.memberservice.support.MemberFixture.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("deprecation")
class MemberServiceTest {

    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(
                memberRepository,
                kafkaTemplate
        );
    }

    @Test
    void deleteMemberTest() {
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(user()));

        doNothing()
                .when(memberRepository)
                .deleteById(any());

        given(kafkaTemplate.send(any(), any()))
                .willReturn(null);

        memberService.deleteMember(Mono.just(deleteMemberRequest())).block();

        verify(memberRepository).deleteById(any());
    }

    @Test
    void noMember_deleteMemberFailTest() {
        given(memberRepository.findByEmail(any()))
                .willThrow(MemberNotFoundException.of(EMAIL));

        assertThrows(
                MemberNotFoundException.class,
                () -> memberService.deleteMember(Mono.just(deleteMemberRequest())).block()
        );
    }
}