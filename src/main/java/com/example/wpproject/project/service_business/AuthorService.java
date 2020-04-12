package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.Author;
import com.example.wpproject.project.model.Category;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();
    Author findById(Long id);
    Author save(Author author);
    Author update(Long id, Author author);
    void deleteById(Long id);
}
