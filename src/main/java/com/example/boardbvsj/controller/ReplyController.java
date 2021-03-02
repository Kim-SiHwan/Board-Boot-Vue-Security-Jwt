package com.example.boardbvsj.controller;

import com.example.boardbvsj.dto.replyDto.ReplyRequestDto;
import com.example.boardbvsj.dto.replyDto.ReplyResponseDto;
import com.example.boardbvsj.dto.replyDto.ReplyUpdateDto;
import com.example.boardbvsj.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replies")
public class ReplyController {

    private final ReplyService replyService;


    @GetMapping("/{boardId}")
    public ResponseEntity<List<ReplyResponseDto>> getRepliesByBoardId(@PathVariable Long boardId){
        return new ResponseEntity<>(replyService.findAll(boardId),HttpStatus.OK);
    }


    @PostMapping
    public void createReply(@RequestBody @Valid ReplyRequestDto replyRequestDto){
        replyService.createReply(replyRequestDto);
    }

    @DeleteMapping("/{replyId}")
    public void deleteReply(@PathVariable Long replyId){
        replyService.deleteReply(replyId);
    }


    @PutMapping
    public void updateReply(@RequestBody @Valid ReplyUpdateDto replyUpdateDto){
        replyService.updateReply(replyUpdateDto);
    }}
