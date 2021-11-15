package com.gachon_food.repository;

import com.gachon_food.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);
}
