package com.example.boardbvsj.repository;

import com.example.boardbvsj.entity.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
    @Query("select r from Reply r where r.board.id =:boardId order by r.id desc ")
    @EntityGraph(attributePaths = {"board","member"},type = EntityGraph.EntityGraphType.LOAD)
    List<Reply> findAllByBoard_Id(Long boardId);
}
