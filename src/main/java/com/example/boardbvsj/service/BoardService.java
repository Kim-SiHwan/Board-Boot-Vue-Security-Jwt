package com.example.boardbvsj.service;

import com.example.boardbvsj.dto.boardDto.BoardResponseDto;
import com.example.boardbvsj.dto.boardDto.BoardUpdateRequestDto;
import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.repository.BoardRepository;
import com.example.boardbvsj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public List<BoardResponseDto> findAll(){
        List<Board> boards = boardRepository.findAllDesc();
        List<BoardResponseDto> list = boards.stream()
                .map(m -> new BoardResponseDto(m))
                .collect(Collectors.toList());
        return list;
    }

    public BoardResponseDto findOne(Long boardId){
        Board board= boardRepository.findById(boardId).get();
        return new BoardResponseDto(board);
    }

    @Transactional
    public Long createBoard(Board board, String username){
        Optional<Member> member = memberRepository.findByUsername(username);
        board.setMember(member.get());
        member.get().getBoards().add(board);
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional
    public void deleteBoard(Long boardId){
        Board board= boardRepository.findById(boardId).get();
        boardRepository.delete(board);
    }

    @Transactional
    public void updateBoard(Long boardId,BoardUpdateRequestDto boardUpdateRequestDto){
        Board board= boardRepository.findById(boardId).get();
        board.changeText(boardUpdateRequestDto.getUpdateTitle(), boardUpdateRequestDto.getUpdateContent());
    }
    @Transactional
    public void addReadCount(Long boardId){
        Board board = boardRepository.findById(boardId).get();
        board.addReadCount();
    }


}
