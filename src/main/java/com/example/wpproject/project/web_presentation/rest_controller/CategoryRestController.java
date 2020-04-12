package com.example.wpproject.project.web_presentation.rest_controller;

import com.example.wpproject.project.model.Category;
import com.example.wpproject.project.service_business.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAllCategories(){
        return this.categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id){
        return this.categoryService.findById(id);
    }

    @PostMapping
    public Category saveCategory(Category category){
        return this.categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, Category category){
        return this.categoryService.update(id,category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        this.categoryService.deleteById(id);
    }
}
