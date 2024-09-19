package me.jun.memberservice.core.domain.repository;

import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.Role;
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
    void findByEmailTest() {
        Member expected = Member.builder()
                .id(MEMBER_ID)
                .name(NAME)
                .email(EMAIL)
                .role(Role.USER)
                .password(password())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();

        memberRepository.save(
                user().toBuilder()
                        .createdAt(null)
                        .updatedAt(null)
                        .build()
        );

        Member member = memberRepository.findByEmail(EMAIL).get();

        assertAll(
                () -> assertThat(member)
                        .isEqualToIgnoringGivenFields(expected, "createdAt", "updatedAt"),
                () -> assertThat(member.getCreatedAt())
                        .isAfterOrEqualTo(expected.getCreatedAt()),
                () -> assertThat(member.getUpdatedAt())
                        .isAfterOrEqualTo(expected.getUpdatedAt())
        );
    }
}