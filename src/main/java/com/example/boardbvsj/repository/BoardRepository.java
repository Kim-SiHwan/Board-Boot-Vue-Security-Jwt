package com.example.boardbvsj.repository;

import com.example.boardbvsj.entity.Board;
import com.example.boardbvsj.entity.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, QuerydslPredicateExecutor<Board> {
    @Query("select b from Board b where b.boardLikes.size>=3 order by b.id desc ")
    List<Board> findAllDesc();

    public default Predicate makePredicate(String type, String keyword){
        BooleanBuilder b= new BooleanBuilder();
        QBoard board= QBoard.board;
        b.and(board.id.gt(0));
        if(keyword.equals("default"))
            return b;

        b.and(board.title.like("%" + keyword + "%").
                or(board.member.username.like("%" + keyword + "%"))
        .or(board.content.like("%" + keyword + "%")));
        return b;
    }


}
