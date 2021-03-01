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
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;



    @GetMapping("/board")
    public ResponseEntity<List<BoardResponseDto>> getAllByKeyword(@RequestBody KeywordDto keywordDto){
        System.out.println("키워드 : " +keywordDto.getKeyword());
        return new ResponseEntity<>(boardService.findAll(keywordDto.getKeyword()),HttpStatus.OK);
    }

/*
    @GetMapping("/board/best")
    public ResponseEntity getBests(){
        return new ResponseEntity(getBoards(null,1),HttpStatus.OK);
    }
*/


    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardResponseDto> getOne(@PathVariable("boardId") Long boardId){
        BoardResponseDto responseDto = boardService.findOne(boardId);
        return new ResponseEntity<>(boardService.findOne(boardId),HttpStatus.OK);
    }

    @PostMapping("/board")
    public void createBoard(@RequestBody BoardRequestDto requestDto){
        boardService.createBoard(requestDto.toEntity(requestDto), requestDto.getUsername());
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId){
        String msg="";
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        BoardResponseDto boardResponseDto = boardService.findOne(boardId);
        if(username.equals(boardResponseDto.getUsername())||username.equals("admin")){
            msg="삭제완료";
            boardService.deleteBoard(boardId);
            return new ResponseEntity<>(msg,HttpStatus.OK);
        }else{
            msg="작성자만 삭제할 수 있습니다.";
            return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/board/{boardId}")
    public ResponseEntity<String> updateBoard(@PathVariable("boardId")Long boardId,
                                              @RequestBody BoardUpdateRequestDto updateRequestDto){
        String msg="";
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        BoardResponseDto boardResponseDto = boardService.findOne(boardId);
        if(username.equals(boardResponseDto.getUsername())){
            msg="수정완료";
            boardService.updateBoard(boardId,updateRequestDto);
            return new ResponseEntity<>(msg,HttpStatus.OK);
        }else{
            msg="작성자만 수정할 수 있습니다.";
            return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
        }
    }

    @Getter
    static class KeywordDto{
        private String keyword;
    }

}
