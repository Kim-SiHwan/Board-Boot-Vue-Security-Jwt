package com.example.boardbvsj.config;

import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationRunnerConfig implements ApplicationRunner {
    private final PasswordEncoder pwEncoder;
    private final MemberService memberService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member admin = Member.builder()
                .username("admin")
                .nickname("관리자")
                .password(pwEncoder.encode("admin"))
                .role("ROLE_ADMIN")
                .build();
        Member user = Member.builder()
                .username("user")
                .nickname("김시환")
                .password(pwEncoder.encode("user"))
                .role("ROLE_USER")
                .build();
        memberService.save(admin);
        memberService.save(user);

    }

}
