package com.example.boardbvsj.dto.boardDto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class BoardUpdateRequestDto {
    private Long boardId;
    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(min = 1, max = 20, message = "제목은 1자~20자 이내로 작성해주세요.")
    private String updateTitle;
    private String updateContent;
}
