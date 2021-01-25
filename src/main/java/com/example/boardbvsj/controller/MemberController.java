package com.example.boardbvsj.controller;

import com.example.boardbvsj.dto.JoinDto;
import com.example.boardbvsj.dto.LoginDto;
import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.jwt.JwtFilter;
import com.example.boardbvsj.jwt.JwtTokenProvider;
import com.example.boardbvsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final AuthenticationManagerBuilder managerBuilder;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/save")
    public ResponseEntity save(@RequestBody JoinDto dto){
        Member member = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .role("ROLE_USER")
                .build();

        return new ResponseEntity(memberService.save(member),HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity authorize(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication auth = managerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt= tokenProvider.createToken(auth);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer "+jwt);
        HashMap<String,String> map =new HashMap<>();
        map.put("token",jwt);
        map.put("username",loginDto.getUsername());
        return new ResponseEntity(map, httpHeaders, HttpStatus.OK);
    }

  


}
