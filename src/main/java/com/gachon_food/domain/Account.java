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
import java.util.ArrayList;
import java.util.List;

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

    @Column(length = 30, nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name="account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles = new ArrayList<>();

    @Column(length = 30, nullable = false)
    private String major;

    @Column(length = 1)
    private String deleteYN;

    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    public Account(AccountDto.AccountRequestDto accountRequestDto){
        this.email = accountRequestDto.getEmail();
        this.password = accountRequestDto.getPassword();
    }

    @Builder
    public Account(String email, String password, List<Role> roles){
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }
}
