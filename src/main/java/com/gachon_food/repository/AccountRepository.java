package com.gachon_food.repository;

import com.gachon_food.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/*
Account Repository (저장소)
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
}
