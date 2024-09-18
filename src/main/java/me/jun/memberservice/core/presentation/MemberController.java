package me.jun.memberservice.core.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.memberservice.core.application.LoginService;
import me.jun.memberservice.core.application.MemberService;
import me.jun.memberservice.core.application.RegisterService;
import me.jun.memberservice.core.application.dto.MemberResponse;
import me.jun.memberservice.core.application.dto.RegisterRequest;
import me.jun.memberservice.core.application.dto.RetrieveMemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    public Mono<ResponseEntity<MemberResponse>> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return registerService.register(
                Mono.fromSupplier(() -> request)
                        .log()
        )
                .log()
                .map(
                        response -> ResponseEntity.ok()
                                .body(response)
                )
                .doOnError(throwable -> log.error("{}", throwable));
    }

    @PostMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<MemberResponse>> retrieveMember(
            @RequestBody @Valid RetrieveMemberRequest request
    ) {
        return memberService.retrieveMember(
                Mono.fromSupplier(() -> request)
                        .log()
        )
                .log()
                .map(response -> ResponseEntity.ok()
                        .body(response))
                .doOnError(throwable -> log.error("{}", throwable));
    }
}
