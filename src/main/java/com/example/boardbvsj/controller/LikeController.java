package com.example.boardbvsj.controller;

import com.example.boardbvsj.dto.boardDto.BoardLikeDto;
import com.example.boardbvsj.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity setLike(@RequestBody BoardLikeDto boardLikeDto){
        String msg =likeService.pushLike(boardLikeDto.getBoardId(), boardLikeDto.getUsername());
        return new ResponseEntity(msg, HttpStatus.OK);
    }
}
