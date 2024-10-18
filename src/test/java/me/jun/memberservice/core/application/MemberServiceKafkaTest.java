package me.jun.memberservice.core.application;

import me.jun.memberservice.common.config.KafkaConsumer;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static me.jun.memberservice.support.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest
@EmbeddedKafka(
        topics = "member.delete",
        ports = 9092,
        brokerProperties = "listeners=PLAINTEXT://localhost:9092"
)
public class MemberServiceKafkaTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    void deleteMemberTest() throws InterruptedException {
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(user()));

        doNothing()
                .when(memberRepository)
                .deleteById(any());

        memberService.deleteMember(Mono.just(deleteMemberRequest())).block();

        verify(memberRepository)
                .deleteById(MEMBER_ID);

        Thread.sleep(2_000);

        assertThat(kafkaConsumer.getId())
                .isEqualTo(MEMBER_ID);
    }
}
