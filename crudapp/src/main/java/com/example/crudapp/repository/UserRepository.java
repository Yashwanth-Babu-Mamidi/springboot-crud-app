package com.example.crudapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crudapp.entity.User;

public interface UserRepository
        extends JpaRepository<User,Long>{

    Optional<User> findByUsername(String username);

}