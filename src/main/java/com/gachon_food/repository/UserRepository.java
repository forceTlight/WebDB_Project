package com.gachon_food.repository;

import com.gachon_food.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
