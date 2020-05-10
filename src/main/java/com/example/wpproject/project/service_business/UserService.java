package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findById(String userId);
    User save(User user);
    User update(String userId, User user);
    void deleteById(String userId);
}
