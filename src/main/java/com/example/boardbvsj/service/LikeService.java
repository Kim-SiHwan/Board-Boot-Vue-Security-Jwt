package com.example.boardbvsj.service;

import com.example.boardbvsj.dto.boardDto.BoardLikeDto;
import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.entity.BoardLike;
import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.exception.customException.BoardNotFoundException;
import com.example.boardbvsj.exception.customException.UserNotFoundException;
import com.example.boardbvsj.repository.BoardLikeRepository;
import com.example.boardbvsj.repository.BoardRepository;
import com.example.boardbvsj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public String pushLike(BoardLikeDto likeDto){

        BoardLike boardLike = boardLikeRepository.findByBoardIdAndMember_Username(likeDto.getBoardId(),likeDto.getUsername())
                .orElseGet(BoardLike::new);
        if(boardLike.getId()==null){
            Board board = boardRepository.findById(likeDto.getBoardId()).
                    orElseThrow(BoardNotFoundException::new);
            Member member = memberRepository.findByUsername(likeDto.getUsername())
                    .orElseThrow(UserNotFoundException::new);
            boardLike.addMember(member);
            boardLike.addBoard(board);
            addLike(boardLike);
            return "좋아요!";
        }
        removeLike(boardLike.getId());
        return "좋아요 취소";


    }

    @Transactional
    public void addLike(BoardLike boardLike){
        boardLikeRepository.save(boardLike);
    }

    @Transactional
    public void removeLike(Long id){
        boardLikeRepository.deleteById(id);
    }
}
