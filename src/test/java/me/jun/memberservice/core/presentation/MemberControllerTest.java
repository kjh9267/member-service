package me.jun.memberservice.core.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.memberservice.core.application.LoginService;
import me.jun.memberservice.core.application.MemberService;
import me.jun.memberservice.core.application.RegisterService;
import me.jun.memberservice.core.application.dto.LoginRequest;
import me.jun.memberservice.core.application.dto.RegisterRequest;
import me.jun.memberservice.core.application.exception.DuplicatedEmailException;
import me.jun.memberservice.core.application.exception.MemberNotFoundException;
import me.jun.memberservice.core.domain.exception.WrongPasswordException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static me.jun.memberservice.support.MemberFixture.*;
import static me.jun.memberservice.support.TokenFixture.TOKEN;
import static me.jun.memberservice.support.TokenFixture.tokenResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureWebTestClient

public class MemberControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private MemberService memberService;

    @MockBean
    private RegisterService registerService;

    @MockBean
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void registerTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(registerRequest());

        given(registerService.register(any()))
                .willReturn(Mono.just(memberResponse()));

        webClient.post()
                .uri("/api/member/register")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("id").exists()
                .jsonPath("email").exists()
                .jsonPath("name").exists()
                .jsonPath("role").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void noContent_registerFailTest() {
        webClient.post()
                .uri("/api/member/register")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    void wrongContent_registerFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(
                RegisterRequest.builder()
                        .name("")
                        .email("")
                        .password("")
                        .build()
        );

        webClient.post()
                .uri("/api/member/register")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    void duplicatedEmail_registerFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(registerRequest());

        given(registerService.register(any()))
                .willThrow(DuplicatedEmailException.of(EMAIL));

        webClient.post()
                .uri("/api/member/register")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void retrieveMemberTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(retrieveMemberRequest());

        given(memberService.retrieveMember(any()))
                .willReturn(Mono.just(memberResponse()));

        webTestClient.post()
                .uri("/api/member")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("id").exists()
                .jsonPath("email").exists()
                .jsonPath("name").exists()
                .jsonPath("role").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void noMember_retrieveMemberFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(retrieveMemberRequest());

        given(memberService.retrieveMember(any()))
                .willThrow(MemberNotFoundException.of(EMAIL));

        webTestClient.post()
                .uri("/api/member")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void noContent_retrieveMemberFailTest() {
        webClient.post()
                .uri("/api/member")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    void loginTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(loginRequest());

        given(loginService.login(any()))
                .willReturn(Mono.just(tokenResponse()));

        webClient.post()
                .uri("/api/member/login")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("token").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void noContent_loginFailTest() {
        webTestClient.post()
                .uri("/api/member/login")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    void wrongContent_loginFailTest() throws JsonProcessingException {
        String Content = objectMapper.writeValueAsString(
                LoginRequest.builder()
                        .email("")
                        .password("")
                        .build()
        );

        webClient.post()
                .uri("/api/member/login")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(Content)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    void noMember_loginFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(loginRequest());

        given(loginService.login(any()))
                .willThrow(MemberNotFoundException.of(EMAIL));

        webTestClient.post()
                .uri("/api/member/login")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }

    @Test
    void invalidPassword_loginFailTest() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(loginRequest());

        given(loginService.login(any()))
                .willThrow(WrongPasswordException.of(TOKEN));

        webTestClient.post()
                .uri("/api/member/login")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("detail").exists()
                .consumeWith(System.out::println);
    }
}
