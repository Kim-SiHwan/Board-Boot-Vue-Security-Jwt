package com.example.boardbvsj.config;

import com.example.boardbvsj.dto.boardDto.BoardLikeDto;
import com.example.boardbvsj.dto.replyDto.ReplyRequestDto;
import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.repository.MemberRepository;
import com.example.boardbvsj.service.BoardService;
import com.example.boardbvsj.service.LikeService;
import com.example.boardbvsj.service.MemberService;
import com.example.boardbvsj.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ApplicationRunnerConfig implements ApplicationRunner {
    private final PasswordEncoder pwEncoder;
    private final MemberRepository memberRepository;
    private final BoardService boardService;
    private final ReplyService replyService;
    private final LikeService likeService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member admin = Member.builder()
                .username("admin")
                .nickname("관리자")
                .password(pwEncoder.encode("admin"))
                .role("ROLE_ADMIN")
                .build();
        Member user = Member.builder()
                .username("user")
                .nickname("김시환")
                .password(pwEncoder.encode("user"))
                .role("ROLE_USER")
                .build();
        Member user2 = Member.builder()
                .username("user2")
                .nickname("김시환")
                .password(pwEncoder.encode("user2"))
                .role("ROLE_USER")
                .build();
        memberRepository.save(admin);
        memberRepository.save(user);
        memberRepository.save(user2);

        for(int i=1; i<=3; i++){
            Board board = Board.makeBoard()
                    .title("SampleTile"+i)
                    .content("SampleContent"+i)
                    .createDate(LocalDateTime.now())
                    .build();
            boardService.createBoard(board,"user");
            int r= (int)(Math.random()*20)+1;
            for(int j=0; j<r; j++){

                ReplyRequestDto requestDto = ReplyRequestDto
                        .builder()
                        .boardId(Long.parseLong(String.valueOf(i)))
                        .content("SAMPLE REPLY"+i)
                        .username("user")
                        .build();

                replyService.createReply(requestDto);
            }


        }

  /*      likeService.pushLike(300L,"user");
        likeService.pushLike(300L,"admin");
        likeService.pushLike(300L,"user2");
*/
        BoardLikeDto boardLikeDto = new BoardLikeDto();
        boardLikeDto.setBoardId(3L);
        boardLikeDto.setUsername("user");
        likeService.pushLike(boardLikeDto);
        boardLikeDto.setBoardId(3L);
        boardLikeDto.setUsername("admin");
        likeService.pushLike(boardLikeDto);
        boardLikeDto.setBoardId(3L);
        boardLikeDto.setUsername("user2");
        likeService.pushLike(boardLikeDto);
        boardLikeDto.setBoardId(1L);
        boardLikeDto.setUsername("user");
        likeService.pushLike(boardLikeDto);
        boardLikeDto.setBoardId(1L);
        boardLikeDto.setUsername("admin");
        likeService.pushLike(boardLikeDto);
        boardLikeDto.setBoardId(1L);
        boardLikeDto.setUsername("user2");
        likeService.pushLike(boardLikeDto);
/*
        likeService.pushLike(10L,"user");
        likeService.pushLike(10L,"admin");
        likeService.pushLike(10L,"user2");

        likeService.pushLike(200L,"user");
        likeService.pushLike(200L,"admin");
        likeService.pushLike(200L,"user2");*/


    }

}
