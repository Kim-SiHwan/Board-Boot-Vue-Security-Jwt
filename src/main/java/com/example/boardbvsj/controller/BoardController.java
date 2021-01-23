package com.example.boardbvsj.controller;

import com.example.boardbvsj.dto.boardDto.BoardRequestDto;
import com.example.boardbvsj.dto.boardDto.BoardResponseDto;
import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

    public List<BoardResponseDto> getBoards(){
        return boardService.findAll();
    }

    @GetMapping("/board")
    public ResponseEntity getAll(){
        return new ResponseEntity(getBoards(), HttpStatus.OK);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity getOne(@PathVariable("boardId") Long boardId){
        BoardResponseDto responseDto = boardService.findOne(boardId);
        return new ResponseEntity(responseDto,HttpStatus.OK);
    }

    @PostMapping("/board")
    public ResponseEntity createBoard(@RequestBody BoardRequestDto requestDto){
        Long boardId=boardService.createBoard(requestDto.toEntity(requestDto), requestDto.getUsername());
        return new ResponseEntity(boardId,HttpStatus.CREATED);

    }

}
