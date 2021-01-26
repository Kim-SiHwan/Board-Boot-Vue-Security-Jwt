package com.example.boardbvsj.service;

import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.entity.BoardLike;
import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.repository.BoardLikeRepository;
import com.example.boardbvsj.repository.BoardRepository;
import com.example.boardbvsj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public String pushLike(Long boardId, String username){
        Board board = boardRepository.findById(boardId).get();
        String msg="";
        Optional<Member> member= memberRepository.findByUsername(username);
        System.out.println(member);
        BoardLike boardLike= new BoardLike();
        boardLike.setBoard(board);
        boardLike.setMember(member.get());
        Optional<BoardLike> getBoardLike = boardLikeRepository.findByBoardIdAndMember_Username(boardId,username);
        if(getBoardLike.isEmpty()){
            addLike(boardLike);
            msg = "좋아요!";
        }else{
            removeLike(getBoardLike.get().getId());
            msg = "좋아요 취소";
        }
        return msg;

    }

    @Transactional
    public void addLike(BoardLike boardLike){
        boardLikeRepository.save(boardLike);
    }

    @Transactional
    public void removeLike(Long id){
        BoardLike boardLike = boardLikeRepository.findById(id).get();
        boardLikeRepository.delete(boardLike);
    }
}
