package com.example.boardbvsj.dto.replyDto;


import com.example.boardbvsj.entity.Reply;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
public class ReplyRequestDto {

    @NotEmpty(message = "댓글은 공백일 수 없습니다.\n\r 댓글을 입력해주세요.")
    @Size(min = 1, max = 100, message = "댓글은 1자 이상, 100자 이하로 입력해주세요.")
    private String content;

    private LocalDateTime createDate;

    private String username;

    private Long boardId;

    public Reply toEntity(ReplyRequestDto replyRequestDto) {
        return Reply.createReply()
                .content(replyRequestDto.getContent())
                .createDate(LocalDateTime.now())
                .build();
    }

}
