package com.example.boardbvsj.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString(exclude = {"member", "board"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    @Builder(builderClassName = "createReply", builderMethodName = "createReply")
    public Reply(Long id, String content, LocalDateTime createDate) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
    }

    public void addMember(Member member) {
        this.member = member;
        this.member.getReplies().add(this);
    }
    public void changeText(String content) {
        this.content = content;
    }

    public void addBoard(Board board) {
        this.board = board;
        this.board.getReplies().add(this);
    }


}
