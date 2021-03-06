package com.example.boardbvsj.service;

import com.example.boardbvsj.dto.replyDto.ReplyRequestDto;
import com.example.boardbvsj.dto.replyDto.ReplyResponseDto;
import com.example.boardbvsj.dto.replyDto.ReplyUpdateDto;
import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.entity.Reply;
import com.example.boardbvsj.exception.customException.BoardNotFoundException;
import com.example.boardbvsj.exception.customException.DifferentUsernameException;
import com.example.boardbvsj.exception.customException.ReplyNotFoundException;
import com.example.boardbvsj.exception.customException.UserNotFoundException;
import com.example.boardbvsj.repository.BoardRepository;
import com.example.boardbvsj.repository.MemberRepository;
import com.example.boardbvsj.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public List<ReplyResponseDto> findAll(Long boardId) {
        List<Reply> replies = replyRepository.findAllByBoard_Id(boardId);
        List<ReplyResponseDto> list = replies.stream()
                .map(ReplyResponseDto::new)
                .collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }

    @Transactional
    public void createReply(ReplyRequestDto replyRequestDto) {
        Board board = boardRepository.findById(replyRequestDto.getBoardId())
                .orElseThrow(BoardNotFoundException::new);
        Member member = memberRepository.findByUsername(replyRequestDto.getUsername())
                .orElseThrow(UserNotFoundException::new);
        Reply reply = replyRequestDto.toEntity(replyRequestDto);
        reply.addBoard(board);
        reply.addMember(member);
        replyRepository.save(reply);
    }


    @Transactional
    public void deleteReply(Long replyId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(ReplyNotFoundException::new);
        if (!username.equals("admin") && !username.equals(reply.getMember().getUsername())) {
            throw new DifferentUsernameException();
        }
        replyRepository.delete(reply);
    }

    @Transactional
    public void updateReply(ReplyUpdateDto replyUpdateDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Reply reply = replyRepository.findById(replyUpdateDto.getReplyId())
                .orElseThrow(ReplyNotFoundException::new);
        if (!username.equals(reply.getMember().getUsername())) {
            throw new DifferentUsernameException();
        }
        reply.changeText(replyUpdateDto.getContent());
    }
}
