package com.example.wpproject.project.web_presentation.rest_controller;

import com.example.wpproject.project.model.Author;
import com.example.wpproject.project.service_business.AuthorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> findAllAuthors(){
        return this.authorService.findAll();
    }

    @GetMapping("/{id}")
    public Author findAuthorById(@PathVariable Long id){
        return this.authorService.findById(id);
    }

    @PostMapping
    public Author saveAuthor(Author author){
        return this.authorService.save(author);
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, Author author){
        return this.authorService.update(id,author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id){
        this.authorService.deleteById(id);
    }



}
