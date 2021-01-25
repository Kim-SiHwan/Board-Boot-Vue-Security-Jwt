package com.example.boardbvsj.controller;

import com.example.boardbvsj.dto.boardDto.BoardRequestDto;
import com.example.boardbvsj.dto.boardDto.BoardResponseDto;
import com.example.boardbvsj.dto.boardDto.BoardSearchDto;
import com.example.boardbvsj.dto.boardDto.BoardUpdateRequestDto;
import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

    public List<BoardResponseDto> getBoards(BoardSearchDto searchDto){
        return boardService.findAll(searchDto);
    }

    @GetMapping("/board")
    public ResponseEntity getAll(BoardSearchDto searchDto){
        return new ResponseEntity(getBoards(searchDto), HttpStatus.OK);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity getOne(@PathVariable("boardId") Long boardId){
        BoardResponseDto responseDto = boardService.findOne(boardId);
        boardService.addReadCount(boardId);
        return new ResponseEntity(responseDto,HttpStatus.OK);
    }

    @PostMapping("/board")
    public ResponseEntity createBoard(@RequestBody BoardRequestDto requestDto){
        Long boardId=boardService.createBoard(requestDto.toEntity(requestDto), requestDto.getUsername());
        return new ResponseEntity(boardId,HttpStatus.CREATED);

    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable("boardId")Long boardId){
        String msg="";
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        BoardResponseDto boardResponseDto = boardService.findOne(boardId);
        if(username.equals(boardResponseDto.getUsername())||username.equals("admin")){
            msg="삭제완료";
            boardService.deleteBoard(boardId);
            return new ResponseEntity(msg,HttpStatus.OK);
        }else{
            msg="작성자만 삭제할 수 있습니다.";
            return new ResponseEntity(msg,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/board/{boardId}")
    public ResponseEntity updateBoard(@PathVariable("boardId")Long boardId,
                                      @RequestBody BoardUpdateRequestDto updateRequestDto){
        String msg="";
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        BoardResponseDto boardResponseDto = boardService.findOne(boardId);
        if(username.equals(boardResponseDto.getUsername())){
            msg="수정완료";
            boardService.updateBoard(boardId,updateRequestDto);
            return new ResponseEntity(msg,HttpStatus.OK);
        }else{
            msg="작성자만 수정할 수 있습니다.";
            return new ResponseEntity(msg,HttpStatus.BAD_REQUEST);
        }
    }

}
