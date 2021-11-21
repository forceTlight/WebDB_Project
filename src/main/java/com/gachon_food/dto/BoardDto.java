package com.gachon_food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
게시판 정보 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private int boardId;
    private String title;
    private String content;
    private String deleteYN;
}
