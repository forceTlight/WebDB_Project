package com.gachon_food.domain;

import com.gachon_food.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String major;

    @Column(length = 1)
    private String deleteYN;

    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    public Account(AccountDto.AccountRequestDto LoginFormDto){
        this.email = LoginFormDto.getEmail();
        this.password = LoginFormDto.getPassword();
        this.major = LoginFormDto.getMajor();
        this.name = LoginFormDto.getName();
    }

    @Builder
    public Account(String email, String password, String major){
        this.email = email;
        this.password = password;
        this.major = major;
        this.name = name;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }
}
