package me.jun.memberservice.core.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.memberservice.core.application.dto.MemberResponse;
import me.jun.memberservice.core.application.dto.RegisterRequest;
import me.jun.memberservice.core.application.exception.DuplicatedEmailException;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService {

    private final MemberRepository memberRepository;

    public Mono<MemberResponse> register(Mono<RegisterRequest> requestMono) {
        return requestMono.log()
                .map(request -> request.toEntity())
                .map(
                        member -> {
                            try {
                                return memberRepository.save(member);
                            }
                            catch (DataIntegrityViolationException e) {
                                throw new DuplicatedEmailException(member.getEmail());
                            }
                        }
                )
                .map(MemberResponse::of)
                .doOnError(throwable -> log.info("{}", throwable));
    }
}
