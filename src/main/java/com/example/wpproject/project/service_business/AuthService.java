package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.User;

public interface AuthService {

    User getCurrentUser();
    Integer getCurrentUserId();
}
