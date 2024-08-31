package me.jun.memberservice.core.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.memberservice.core.application.dto.MemberResponse;
import me.jun.memberservice.core.application.dto.RetrieveMemberRequest;
import me.jun.memberservice.core.application.exception.MemberNotFoundException;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Mono<MemberResponse> retrieveMember(Mono<RetrieveMemberRequest> requestMono) {
        return requestMono.log()
                .map(
                        request -> memberRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new MemberNotFoundException(request.getEmail()))
        )
                .map(MemberResponse::of)
                .doOnError(throwable -> log.info("{}", throwable));
    }
}
