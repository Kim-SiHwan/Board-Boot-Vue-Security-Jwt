package com.example.boardbvsj.service;

import com.example.boardbvsj.dto.memberDto.JoinDto;
import com.example.boardbvsj.dto.memberDto.LoginDto;
import com.example.boardbvsj.dto.memberDto.MemberResponseDto;
import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.exception.customException.UsernameDuplicatedException;
import com.example.boardbvsj.config.jwt.JwtTokenProvider;
import com.example.boardbvsj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder managerBuilder;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public void save(JoinDto joinDto) {
        Member member = joinDto.toEntity(joinDto,passwordEncoder);
        if(!validateDuplicateMember(member)){
            throw new UsernameDuplicatedException();
        }
        repository.save(member);

    }

    @Transactional
    public MemberResponseDto login(LoginDto loginDto){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = managerBuilder.getObject().authenticate(token);
        String jwt = tokenProvider.createToken(authentication);
        return new MemberResponseDto(jwt,authentication.getName());
    }


    private Boolean validateDuplicateMember(Member member) {
        Optional<Member> getMember = repository.findByUsername(member.getUsername());
        return getMember.isEmpty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole()));
        return new User(member.getUsername(), member.getPassword(), authorities);
    }

}
