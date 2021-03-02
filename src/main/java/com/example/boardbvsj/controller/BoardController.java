package com.example.boardbvsj.controller;

import com.example.boardbvsj.dto.boardDto.BoardRequestDto;
import com.example.boardbvsj.dto.boardDto.BoardResponseDto;
import com.example.boardbvsj.dto.boardDto.BoardUpdateRequestDto;
import com.example.boardbvsj.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;



    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllByKeyword(@RequestParam String keyword){
        return new ResponseEntity<>(boardService.findAll(keyword),HttpStatus.OK);
    }


    @GetMapping("/best")
    public ResponseEntity<List<BoardResponseDto>> getBests(){
        return new ResponseEntity<>(boardService.findAllBest(),HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getOne(@PathVariable Long boardId){
        return new ResponseEntity<>(boardService.findOne(boardId),HttpStatus.OK);
    }

    @PostMapping
    public void createBoard(@RequestBody @Valid BoardRequestDto requestDto){
        boardService.createBoard(requestDto.toEntity(requestDto), requestDto.getUsername());
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
    }

    @PatchMapping
    public void updateBoard(@RequestBody @Valid BoardUpdateRequestDto boardUpdateRequestDto){
        boardService.updateBoard(boardUpdateRequestDto);

    }

    @Getter
    @Setter
    static class KeywordDto{
        private String keyword;
    }



}
