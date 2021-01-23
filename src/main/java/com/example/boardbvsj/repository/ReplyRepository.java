package com.example.boardbvsj.repository;

import com.example.boardbvsj.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
    List<Reply> findAllByBoard_Id(Long boardId);
}
