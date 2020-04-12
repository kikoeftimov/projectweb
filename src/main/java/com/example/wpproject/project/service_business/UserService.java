package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findById(Long id);
    User save(User user);
    User update(Long id, User user);
    void deleteById(Long id);
}
