package com.example.boardbvsj.dto.boardDto;

import com.example.boardbvsj.entity.Board;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardRequestDto {

    private Long boardId;

    private String title;

    private String content;

    private LocalDateTime createDate;

    private int read;

    private String username;


    public Board toEntity(BoardRequestDto boardRequestDto) {
        return Board.makeBoard()
                .id(boardRequestDto.getBoardId())
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .createDate(LocalDateTime.now())
                .read(0)
                .build();
    }


}
