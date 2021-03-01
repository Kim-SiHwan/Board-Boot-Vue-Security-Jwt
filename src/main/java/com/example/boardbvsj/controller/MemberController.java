package com.example.boardbvsj.controller;

import com.example.boardbvsj.dto.memberDto.JoinDto;
import com.example.boardbvsj.dto.memberDto.LoginDto;
import com.example.boardbvsj.dto.memberDto.MemberResponseDto;
import com.example.boardbvsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/save")
    public void save(@RequestBody JoinDto dto){
        memberService.save(dto);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<MemberResponseDto> authorize(@RequestBody LoginDto loginDto){

        return new ResponseEntity<>(memberService.login(loginDto),HttpStatus.OK);
    }

  


}
