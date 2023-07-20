package com.eCom.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCom.Model.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{
    
}
