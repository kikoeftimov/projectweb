package com.example.wpproject.project.web_presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {


    @GetMapping
    public String getLoginPage(){
        return "login";
    }

    @PostMapping
    public String loginUser(HttpServletRequest req){
        String username = req.getParameter("username");
        if(username != null){
            return "courses";
        }
        else{
            return "redirect:/login";
        }
    }
}
