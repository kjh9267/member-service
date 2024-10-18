package me.jun.memberservice.core.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.memberservice.core.application.dto.DeleteMemberRequest;
import me.jun.memberservice.core.application.exception.MemberNotFoundException;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("#{${delete-member-kafka-topic}}")
    private String deleteMemberKafkaTopic;

    public Mono<Void> deleteMember(Mono<DeleteMemberRequest> requestMono) {
        return requestMono.map(
                request -> memberRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> MemberNotFoundException.of(request.getEmail()))
        ).log()
                .doOnNext(member -> memberRepository.deleteById(member.getId())).log()
                .doOnNext(member -> kafkaTemplate.send(deleteMemberKafkaTopic, member.getId().toString()))
                .flatMap(empty -> Mono.empty());
    }
}
