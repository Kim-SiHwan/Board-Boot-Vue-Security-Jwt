package com.example.boardbvsj.dto.replyDto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class ReplyUpdateDto {
    private Long replyId;

    @NotEmpty(message = "댓글은 공백일 수 없습니다.\n\r 댓글을 입력해주세요.")
    @Size(min = 1, max = 100, message = "댓글은 1자 이상, 100자 이하로 입력해주세요.")
    private String content;
}
