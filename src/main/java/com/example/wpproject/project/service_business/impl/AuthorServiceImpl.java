package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.model.Author;
import com.example.wpproject.project.model.exceptions.AuthorNotFoundException;
import com.example.wpproject.project.repository_persistence.AuthorRepository;
import com.example.wpproject.project.service_business.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return this.authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Override
    public Author save(Author author) {
        return this.authorRepository.save(author);
    }

    @Override
    public Author update(Long id, Author author) {
        Author a = this.findById(id);
        a.setName(author.getName());
        a.setSurname(author.getSurname());
        a.setUserList(author.getUserList());
        return this.authorRepository.save(a);
    }

    @Override
    public void deleteById(Long id) {
        this.authorRepository.deleteById(id);
    }
}
