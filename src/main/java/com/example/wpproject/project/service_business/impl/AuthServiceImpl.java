package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.model.User;
import com.example.wpproject.project.repository_persistence.UserRepository;
import com.example.wpproject.project.service_business.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        return this.userRepository.findById("current-user")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("current-user");
                    return this.userRepository.save(user);
                });
    }

    @Override
    public String getCurrentUserId() {
        return this.getCurrentUser().getUsername();
    }
}
