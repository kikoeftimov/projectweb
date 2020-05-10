package com.example.wpproject.project.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class CourseIsAlreadyInShoppingCard extends RuntimeException {
    public CourseIsAlreadyInShoppingCard(String name) {
        super(String.format("The course %s is already in a shopping cart!", name));
    }
}
