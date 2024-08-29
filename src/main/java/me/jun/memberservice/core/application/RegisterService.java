package me.jun.memberservice.core.application;

import lombok.RequiredArgsConstructor;
import me.jun.memberservice.core.application.dto.RegisterRequest;
import me.jun.memberservice.core.domain.Member;
import me.jun.memberservice.core.domain.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService {

    private final MemberRepository memberRepository;

    public UserDetails register(RegisterRequest request) {
        Member member = request.toEntity();
        return memberRepository.save(member);
    }
}
