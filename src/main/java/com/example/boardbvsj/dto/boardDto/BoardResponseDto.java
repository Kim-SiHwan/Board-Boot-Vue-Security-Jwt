package com.example.boardbvsj.dto.boardDto;

import com.example.boardbvsj.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private String username;
    private int read;
    private int replyCount;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createDate = board.getCreateDate();
        this.username = board.getMember().getUsername();
        this.replyCount = board.getReplies().size();
        this.read = board.getRead();
    }
}
