package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.model.Role;
import com.example.wpproject.project.model.User;
import com.example.wpproject.project.model.exceptions.UserNotFoundException;
import com.example.wpproject.project.repository_persistence.RoleRepository;
import com.example.wpproject.project.repository_persistence.UserRepository;
import com.example.wpproject.project.service_business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(Integer userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public void save(User user) {

        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus("VERIFIED");
        Role userRole = roleRepository.findByRole("SITE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        this.userRepository.save(user);
    }

    @Override
    public User update(Integer userId, User user) {
        User u = this.findById(userId);
        u.setName(user.getName());
        return this.userRepository.save(u);
    }

    @Override
    public void deleteById(Integer userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public boolean isUserAlreadyPresent(User user) {
        return false;
    }
}
