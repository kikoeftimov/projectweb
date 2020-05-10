package com.example.wpproject.project.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ShoppingCardIsNotActiveException extends RuntimeException {
    public ShoppingCardIsNotActiveException(String username) {
        super(String.format("Shopping card for user: %s is not active", username));
    }
}
