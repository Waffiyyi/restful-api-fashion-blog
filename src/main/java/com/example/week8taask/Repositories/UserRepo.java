package com.example.week8taask.Repositories;

import com.example.week8taask.DTOs.LoginDTO;
import com.example.week8taask.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    LoginDTO findByEmail(String email);
    Optional<User> findById(Long id);
}

