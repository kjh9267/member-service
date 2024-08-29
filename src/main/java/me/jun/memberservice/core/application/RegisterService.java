package me.jun.memberservice.core.application;

import lombok.RequiredArgsConstructor;
import me.jun.memberservice.core.application.dto.MemberResponse;
import me.jun.memberservice.core.application.dto.RegisterRequest;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService {

    private final MemberRepository memberRepository;

    public Mono<MemberResponse> register(Mono<RegisterRequest> requestMono) {
        return requestMono.map(request -> request.toEntity())
                .map(member -> memberRepository.save(member))
                .map(MemberResponse::of);
    }
}
