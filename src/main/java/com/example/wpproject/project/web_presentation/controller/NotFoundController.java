package com.example.wpproject.project.web_presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notFound")
public class NotFoundController {

    @GetMapping
    public String indexPage() {
        return "index";
    }

}
