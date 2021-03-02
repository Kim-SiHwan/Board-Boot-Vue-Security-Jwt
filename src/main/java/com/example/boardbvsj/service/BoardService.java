package com.example.boardbvsj.service;

import com.example.boardbvsj.dto.boardDto.BoardResponseDto;
import com.example.boardbvsj.dto.boardDto.BoardUpdateRequestDto;
import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.exception.customException.BoardNotFoundException;
import com.example.boardbvsj.exception.customException.DifferentUsernameException;
import com.example.boardbvsj.exception.customException.UserNotFoundException;
import com.example.boardbvsj.repository.BoardRepository;
import com.example.boardbvsj.repository.MemberRepository;
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
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public List<BoardResponseDto> findAll(String keyword){
        List<Board>boards = boardRepository.findAllSearchResult(keyword);
       List<BoardResponseDto> list = boards.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }
    public List<BoardResponseDto> findAllBest(){

        List<Board> boards = boardRepository.findAllDesc();

        List<BoardResponseDto> list = boards.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }

    public BoardResponseDto findOne(Long boardId){
        Board board= boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        addReadCount(boardId);

        return new BoardResponseDto(board);
    }

    @Transactional
    public void createBoard(Board board, String username){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        board.addMember(member);
        member.getBoards().add(board);
        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long boardId){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        Board board= boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        if (!username.equals("admin") && !username.equals(board.getMember().getUsername())) {
            throw new DifferentUsernameException();
        }
        boardRepository.delete(board);
    }

    @Transactional
    public void updateBoard(BoardUpdateRequestDto boardUpdateRequestDto){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        Board board= boardRepository.findById(boardUpdateRequestDto.getBoardId())
                .orElseThrow(BoardNotFoundException::new);
        if(!username.equals(board.getMember().getUsername())){
            throw new DifferentUsernameException();
        }
        board.changeText(boardUpdateRequestDto.getUpdateTitle(), boardUpdateRequestDto.getUpdateContent());
    }
    @Transactional
    public void addReadCount(Long boardId){
        Board board= boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        board.addReadCount();
    }



}
