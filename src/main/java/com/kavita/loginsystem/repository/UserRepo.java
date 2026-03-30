package com.kavita.loginsystem.repository;

import com.kavita.loginsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);
}