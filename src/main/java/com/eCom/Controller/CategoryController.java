package com.eCom.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCom.Model.Category;
import com.eCom.Services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Category> create(@RequestBody Category category){
        Category newCategory = this.categoryService.create(category);

        return new ResponseEntity<Category>(newCategory,HttpStatus.CREATED);
    }

    @PutMapping("/update/{cId}")
    public ResponseEntity<Category> update(@PathVariable int cId,@RequestBody Category category){
        Category newCategory = this.categoryService.update(cId,category);

        return new ResponseEntity<Category>(newCategory,HttpStatus.OK);
    }

    @GetMapping("/delete/{cId}")
    public ResponseEntity<String> delete(@PathVariable int cId){
        this.categoryService.delete(cId);
        return new ResponseEntity<String>("Successfullly deleted .....",  HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<List<Category>> viewAll(){
        return new ResponseEntity<List<Category>>(this.categoryService.getAllCategories(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/viewById/{cId}")
    public ResponseEntity<Category> getById(@PathVariable int cId){
        return new ResponseEntity<Category>(this.categoryService.getById(cId), HttpStatus.OK);
    }

}
