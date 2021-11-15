package com.gachon_food.repository;

import com.gachon_food.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByEmail(String email);
}
