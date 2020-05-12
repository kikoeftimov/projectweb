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
    public User findUserById(@PathVariable Integer userId){
        return this.userService.findById(userId);
    }

    @PostMapping
    public void saveUser(User user){
        this.userService.save(user);
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable Integer userId, User user){
        return this.userService.update(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable Integer userId){
        this.userService.deleteById(userId);
    }
}
