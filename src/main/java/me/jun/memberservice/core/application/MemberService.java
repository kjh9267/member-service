package me.jun.memberservice.core.application;

import lombok.RequiredArgsConstructor;
import me.jun.memberservice.core.application.dto.MemberResponse;
import me.jun.memberservice.core.application.dto.RetrieveMemberRequest;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Mono<MemberResponse> retrieveMember(Mono<RetrieveMemberRequest> requestMono) {
        return requestMono.map(request -> memberRepository.findByEmail(request.getEmail())
                .orElse(Member.builder().build())
        )
                .map(MemberResponse::of);
    }
}
