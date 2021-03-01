package com.example.boardbvsj.dto.memberDto;

import com.example.boardbvsj.entity.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

    public Member toEntity(JoinDto joinDto, PasswordEncoder passwordEncoder){
        return Member
                .builder()
                .username(joinDto.getUsername())
                .nickname(joinDto.getNickname())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .role("ROLE_USER")
                .build();

    }
}
