package com.example.wpproject.project.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class CardIsAlreadyCreatedForThisUser extends RuntimeException {
    public CardIsAlreadyCreatedForThisUser(String username) {
        super(String.format("Card is already created for user with username: %s", username));
    }
}
