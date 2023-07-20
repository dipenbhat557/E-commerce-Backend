package com.eCom.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCom.Model.Category;
import com.eCom.Model.Product;

public interface ProductRepo extends JpaRepository<Product,Integer>{

    List<Product> findByCategory(Category category);
    
}
