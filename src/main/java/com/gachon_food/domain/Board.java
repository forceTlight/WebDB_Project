package com.gachon_food.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;

    @Column(length = 30,nullable = false)
    private String title;

    @Lob
    @Column
    private String Content;

    @Column(length = 1)
    private String deleteYN;

    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp deleteDate;
}
