package me.jun.memberservice.core.domain.repository;

import me.jun.memberservice.core.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static me.jun.memberservice.support.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@DataJpaTest
@SuppressWarnings("deprecation")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByNameTest() {
        Member expected = Member.builder()
                .id(MEMBER_ID)
                .name(NAME)
                .email(EMAIL)
                .authorities(GRANTED_AUTHORITIES)
                .password(password())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();

        memberRepository.save(user());

        assertAll(
                () -> assertThat(memberRepository.findByName(NAME).get())
                        .isEqualToIgnoringGivenFields(expected, "authorities"),
                () -> assertThat(
                        memberRepository.findByName(NAME).get()
                                .getAuthorities()
                                .toArray()[0]
                                .toString()
                )
                        .isEqualTo(ADMIN),
                () -> assertThat(
                        memberRepository.findByName(NAME).get()
                                .getAuthorities()
                                .toArray()[1]
                                .toString()
                )
                        .isEqualTo(USER)
        );
    }
}