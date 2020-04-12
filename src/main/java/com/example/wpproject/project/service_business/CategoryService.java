package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.Category;
import com.example.wpproject.project.model.Course;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    Category update(Long id, Category category);
    void deleteById(Long id);
}
