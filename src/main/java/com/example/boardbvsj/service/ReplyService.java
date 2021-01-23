package com.example.boardbvsj.service;

import com.example.boardbvsj.dto.replyDto.ReplyResponseDto;
import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.entity.Reply;
import com.example.boardbvsj.repository.BoardRepository;
import com.example.boardbvsj.repository.MemberRepository;
import com.example.boardbvsj.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public List<ReplyResponseDto> findAll(Long boardId){
        List<Reply> replies = replyRepository.findAllByBoard_Id(boardId);
        List<ReplyResponseDto> list = replies.stream()
                .map(m-> new ReplyResponseDto(m))
                .collect(Collectors.toList());
        return list;
    }

    @Transactional
    public void createReply(Reply reply,String username, Long boardId){
        Board board=boardRepository.findById(boardId).get();
        Optional<Member> member=memberRepository.findByUsername(username);
        reply.setBoard(board);
        reply.setMember(member.get());
        replyRepository.save(reply);
    }
}
