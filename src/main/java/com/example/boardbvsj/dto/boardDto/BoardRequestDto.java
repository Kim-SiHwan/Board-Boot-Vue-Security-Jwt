package com.example.boardbvsj.dto.boardDto;

import com.example.boardbvsj.entity.Board;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
public class BoardRequestDto {
    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(min = 1, max = 20, message = "제목은 1자~20자 이내로 작성해주세요.")
    private String title;

    private String content;

    private LocalDateTime createDate;

    private int read;

    private String username;


    public Board toEntity(BoardRequestDto boardRequestDto) {
        return Board.makeBoard()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .createDate(LocalDateTime.now())
                .read(0)
                .build();
    }


}
