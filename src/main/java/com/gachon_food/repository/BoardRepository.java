package com.gachon_food.repository;

import com.gachon_food.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/*
Board Repository (저장소)
 */
public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);

    Page<Board> findByTitleOrContentContaining(String title, String content, Pageable pageable);
}
