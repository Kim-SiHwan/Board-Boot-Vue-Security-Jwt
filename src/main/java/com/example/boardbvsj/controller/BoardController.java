package com.example.boardbvsj.controller;

import com.example.boardbvsj.dto.boardDto.BoardRequestDto;
import com.example.boardbvsj.dto.boardDto.BoardResponseDto;
import com.example.boardbvsj.dto.boardDto.BoardUpdateRequestDto;
import com.example.boardbvsj.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;



    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllByKeyword(@RequestBody KeywordDto keywordDto){
        return new ResponseEntity<>(boardService.findAll(keywordDto.getKeyword()),HttpStatus.OK);
    }

/*
    @GetMapping("/board/best")
    public ResponseEntity getBests(){
        return new ResponseEntity(getBoards(null,1),HttpStatus.OK);
    }
*/


    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getOne(@PathVariable("boardId") Long boardId){
        BoardResponseDto responseDto = boardService.findOne(boardId);
        return new ResponseEntity<>(boardService.findOne(boardId),HttpStatus.OK);
    }

    @PostMapping
    public void createBoard(@RequestBody BoardRequestDto requestDto){
        boardService.createBoard(requestDto.toEntity(requestDto), requestDto.getUsername());
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
    }

    @PatchMapping
    public void updateBoard(@RequestBody BoardUpdateRequestDto boardUpdateRequestDto){
        boardService.updateBoard(boardUpdateRequestDto);

    }

    @Getter
    static class KeywordDto{
        private String keyword;
    }



}
