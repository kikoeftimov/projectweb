package com.example.wpproject.project.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ShoppingCardIsNotActiveException extends RuntimeException {
    public ShoppingCardIsNotActiveException(Integer userId) {
        super(String.format("Shopping card for user: %d is not active", userId));
    }
}
