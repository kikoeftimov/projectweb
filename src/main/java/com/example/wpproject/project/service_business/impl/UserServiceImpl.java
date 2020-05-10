package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.model.User;
import com.example.wpproject.project.model.exceptions.UserNotFoundException;
import com.example.wpproject.project.repository_persistence.UserRepository;
import com.example.wpproject.project.service_business.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User update(String userId, User user) {
        User u = this.findById(userId);
        u.setUsername(user.getUsername());
        return this.userRepository.save(u);
    }

    @Override
    public void deleteById(String userId) {
        this.userRepository.deleteById(userId);
    }
}
