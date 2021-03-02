package com.example.boardbvsj.repository;

import com.example.boardbvsj.entity.BoardLike;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike,Long> {
    @EntityGraph(attributePaths = {"board","member"},type = EntityGraph.EntityGraphType.LOAD)
    Optional<BoardLike> findByBoardIdAndMember_Username(Long boardId,String username);
}
