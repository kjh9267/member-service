package me.jun.memberservice.core.presentation;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.memberservice.core.application.LoginService;
import me.jun.memberservice.core.application.MemberService;
import me.jun.memberservice.core.application.RegisterService;
import me.jun.memberservice.core.application.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static reactor.core.scheduler.Schedulers.boundedElastic;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final RegisterService registerService;

    private final MemberService memberService;

    private final LoginService loginService;

    @PostMapping(
            value = "/register",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Timed(
            value = "member.register",
            longTask = true
    )
    public Mono<ResponseEntity<MemberResponse>> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        Mono<RegisterRequest> requestMono = Mono.fromSupplier(() -> request).log()
                .publishOn(boundedElastic()).log();

        return registerService.register(requestMono).log()
                .map(
                        response -> ResponseEntity.ok()
                                .body(response)
                ).log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @PostMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    @Timed(
            value = "member.retrieve",
            longTask = true
    )
    public Mono<ResponseEntity<MemberResponse>> retrieveMember(
            @RequestBody @Valid RetrieveMemberRequest request
    ) {
        Mono<RetrieveMemberRequest> requestMono = Mono.fromSupplier(() -> request).log()
                .publishOn(boundedElastic()).log();

        return memberService.retrieveMember(requestMono).log()
                .map(
                        response -> ResponseEntity.ok()
                                .body(response)
                ).log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @PostMapping(
            value = "/login",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    @Timed(
            value = "member.login",
            longTask = true
    )
    public Mono<ResponseEntity<TokenResponse>> login(
            @RequestBody @Valid LoginRequest request
    ) {
        Mono<LoginRequest> requestMono = Mono.fromSupplier(() -> request).log()
                .publishOn(boundedElastic()).log();

        return loginService.login(requestMono).log()
                .map(
                        response -> ResponseEntity.ok()
                                .body(response)
                ).log()
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}
