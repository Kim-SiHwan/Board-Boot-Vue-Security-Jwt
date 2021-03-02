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
    @NotNull(message = "아이디는 필수 항목입니다.")
    @Size(min = 3, max = 10, message ="아이디는 3자~10자 이내로 작성해주세요")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 3, max = 12, message = "비밀번호는 3자~12자 이내로 작성해주세요.")
    private String password;



    public Member toEntity(JoinDto joinDto, PasswordEncoder passwordEncoder){
        return Member
                .builder()
                .username(joinDto.getUsername())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .role("ROLE_USER")
                .build();

    }
}
