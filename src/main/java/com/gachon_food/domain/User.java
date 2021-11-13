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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String major;

    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp updateDate;
}
