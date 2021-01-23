package com.example.boardbvsj.controller;

import com.example.boardbvsj.dto.boardDto.BoardResponseDto;
import com.example.boardbvsj.dto.replyDto.ReplyRequestDto;
import com.example.boardbvsj.dto.replyDto.ReplyResponseDto;
import com.example.boardbvsj.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReplyController {

    private final ReplyService replyService;

    public List<ReplyResponseDto> getReplies(Long boardId){
        return replyService.findAll(boardId);
    }

    @GetMapping("/replies/{boardId}")
    public ResponseEntity getRepliesByBoardId(@PathVariable("boardId")Long boardId){
        return new ResponseEntity(getReplies(boardId),HttpStatus.OK);
    }

    @PostMapping("/replies/{boardId}")
    public ResponseEntity createReply(@PathVariable("boardId")Long boardId,
                                      @RequestBody ReplyRequestDto replyRequestDto){
        replyService.createReply(replyRequestDto.toEntity(replyRequestDto), replyRequestDto.getUsername(), boardId);
        return new ResponseEntity(getReplies(boardId),HttpStatus.CREATED);
    }

}
