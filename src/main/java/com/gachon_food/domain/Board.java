package com.gachon_food.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;

    @Column
    private String author;

    @NotNull
    @Size(min = 2, max = 10, message = "제목은 2자이상 30자 이하입니다.")
    private String title;

    @Lob
    @Column
    private String content;

    @Column(length = 1)
    private String deleteYN;

    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp deleteDate;
}
