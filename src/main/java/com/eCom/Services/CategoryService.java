package com.eCom.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCom.Exception.ResourceNotFoundException;
import com.eCom.Model.Category;
import com.eCom.Repositories.CategoryRepo;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Category create(Category category) {
        return categoryRepo.save(category);
    }

    public Category update(int cId, Category category) {

        Category oldCategory = this.categoryRepo.findById(cId)
                .orElseThrow(() -> new ResourceNotFoundException("The requested resource is not found"));

        oldCategory.setTitle(category.getTitle());

        return this.categoryRepo.save(oldCategory);
    }

    public void delete(int cId) {

        Category category = this.categoryRepo.findById(cId)
                .orElseThrow(() -> new ResourceNotFoundException("The requested resource is not found"));

        this.categoryRepo.delete(category);

    }

    public Category getById(int cId) {
        return this.categoryRepo.findById(cId)
                .orElseThrow(() -> new ResourceNotFoundException("The requested resource is not found"));

    }

    public List<Category> getAllCategories() {
        return this.categoryRepo.findAll();
    }

}
