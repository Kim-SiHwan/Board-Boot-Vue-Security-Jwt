package com.example.boardbvsj.dto.memberDto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull(message = "아이디는 필수 항목입니다.")
    private String username;

    @NotNull(message = "비밀번호는 필수 항목입니다.")
    private String password;
}