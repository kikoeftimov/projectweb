package com.example.wpproject.project.web_presentation.rest_controller;

import com.example.wpproject.project.model.User;
import com.example.wpproject.project.service_business.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAllUsers(){
        return this.userService.findAll();
    }

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable String userId){
        return this.userService.findById(userId);
    }

    @PostMapping
    public User saveUser(User user){
        return this.userService.save(user);
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable String userId, User user){
        return this.userService.update(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable String userId){
        this.userService.deleteById(userId);
    }
}
