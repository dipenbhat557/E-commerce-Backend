package com.eCom.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCom.Model.User;

public interface UserRepo extends JpaRepository<User,Integer>{
    
    public Optional<User> findByEmail(String email);
}
