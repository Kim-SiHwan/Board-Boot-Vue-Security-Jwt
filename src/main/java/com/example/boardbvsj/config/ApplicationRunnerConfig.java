package com.example.boardbvsj.config;

import com.example.boardbvsj.dto.boardDto.BoardRequestDto;
import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.entity.Member;
import com.example.boardbvsj.entity.Reply;
import com.example.boardbvsj.service.BoardService;
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
    private final MemberService memberService;
    private final BoardService boardService;
    private final ReplyService replyService;

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
        memberService.save(admin);
        memberService.save(user);

        for(int i=1; i<=10; i++){
            Board board = Board.makeBoard()
                    .title("SampleTile"+i)
                    .content("SampleContent"+i)
                    .createDate(LocalDateTime.now())
                    .build();
            boardService.createBoard(board,"user");
        }

        for(int i=1; i<=20; i++){
            Reply reply = Reply.createReply()
                    .content("SAMPLEREPLY"+i)
                    .createDate(LocalDateTime.now())
                    .build();
            replyService.createReply(reply,"user",10L);
        }




    }

}
