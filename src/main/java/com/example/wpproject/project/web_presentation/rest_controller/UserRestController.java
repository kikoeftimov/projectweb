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

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id){
        return this.userService.findById(id);
    }

    @PostMapping
    public User saveUser(User user){
        return this.userService.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, User user){
        return this.userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        this.userService.deleteById(id);
    }
}
