package com.gachon_food.repository;

import com.gachon_food.domain.Board;
import com.gachon_food.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;
/*
Files Repository (저장소)
 */
public interface FilesRepository extends JpaRepository<Files, Integer> {
    Files findByBoard(Board board);
}
