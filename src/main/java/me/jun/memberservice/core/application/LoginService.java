package me.jun.memberservice.core.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.memberservice.common.security.JwtProvider;
import me.jun.memberservice.core.application.dto.LoginRequest;
import me.jun.memberservice.core.application.dto.TokenResponse;
import me.jun.memberservice.core.application.exception.MemberNotFoundException;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    private final JwtProvider jwtProvider;

    public Mono<TokenResponse> login(Mono<LoginRequest> requestMono) {
        return requestMono
                .map(
                        request -> memberRepository.findByEmail(request.getEmail())
                                .map(member -> member.validatePassword(request.getPassword()))
                                .orElseThrow(() -> MemberNotFoundException.of(request.getEmail()))
                ).log()
                .map(
                        member -> {
                            String token = jwtProvider.createToken(member.getEmail());
                            return TokenResponse.of(token);
                }).log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}
