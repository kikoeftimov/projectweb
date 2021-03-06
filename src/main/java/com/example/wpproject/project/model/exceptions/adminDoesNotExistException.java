package com.example.wpproject.project.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class adminDoesNotExistException extends RuntimeException {
    public adminDoesNotExistException(){
        super("Admin not found");
    }
}
