package com.example.boardbvsj.dto.boardDto;

import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {
    private Long boardId;
    private String updateTitle;
    private String updateContent;
}
