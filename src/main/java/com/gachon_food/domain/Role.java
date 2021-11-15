package com.gachon_food.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Role {
    @Id
    private int roleId;

    @Column(length = 50)
    private String email;

    @ManyToMany(mappedBy = "roles")
    private List<Account> users;
}
