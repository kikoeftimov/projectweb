package com.example.wpproject.project.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(Integer userId, String message) {
        super(String.format("Transaction failed for user %d", userId));
    }
}
