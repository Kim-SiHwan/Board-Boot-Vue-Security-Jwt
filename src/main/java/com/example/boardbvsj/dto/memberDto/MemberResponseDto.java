package com.example.boardbvsj.dto.memberDto;

import lombok.Getter;

@Getter
public class MemberResponseDto {
    private String token;
    private String username;

    public MemberResponseDto(String token, String username){
        this.token = token;
        this.username = username;
    }
}
