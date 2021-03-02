package com.example.boardbvsj.repository;

import com.example.boardbvsj.entity.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long>, QuerydslPredicateExecutor<Board> {
    @Query("select b from Board b where b.boardLikes.size>=3 order by b.id desc ")
    @EntityGraph(attributePaths = {"member","replies","boardLikes"},type = EntityGraph.EntityGraphType.LOAD)
    List<Board> findAllDesc();

    @Override
    @EntityGraph(attributePaths = {"member","replies","boardLikes"},type = EntityGraph.EntityGraphType.LOAD)
    Optional<Board> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"member","replies","boardLikes"},type = EntityGraph.EntityGraphType.LOAD)
    List<Board> findAll();

    @Query("SELECT b FROM Board b WHERE b.title LIKE %?1% OR b.content LIKE %?1% OR b.member.username LIKE %?1%" )
    @EntityGraph(attributePaths = {"member","replies","boardLikes"},type = EntityGraph.EntityGraphType.LOAD)
    List<Board> findAllSearchResult(String keyword);


}
