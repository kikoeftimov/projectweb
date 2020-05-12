package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findById(Integer userId);
    void save(User user);
    User update(Integer userId, User user);
    void deleteById(Integer userId);

    boolean isUserAlreadyPresent(User user);
}
