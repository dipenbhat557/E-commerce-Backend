package com.eCom.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCom.Model.Cart;
import com.eCom.Model.User;

public interface CartRepo extends JpaRepository<Cart, Integer>{

    public Optional<Cart> findByUser(User user);
    
}
