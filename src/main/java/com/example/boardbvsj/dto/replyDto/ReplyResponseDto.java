package com.example.boardbvsj.dto.replyDto;


import com.example.boardbvsj.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto {
    private Long id;
    private String userName;
    private String content;
    private LocalDateTime createDate;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.userName = reply.getMember().getUsername();
        this.content = reply.getContent();
        this.createDate = reply.getCreateDate();
    }
}