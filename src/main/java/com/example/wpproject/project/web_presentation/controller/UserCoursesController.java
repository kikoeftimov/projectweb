package com.example.wpproject.project.web_presentation.controller;

import com.example.wpproject.project.model.Author;
import com.example.wpproject.project.model.Category;
import com.example.wpproject.project.model.Course;
import com.example.wpproject.project.model.Price;
import com.example.wpproject.project.service_business.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class    UserCoursesController {

    private final CourseService courseService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PriceService priceService;
    private final UserService userService;
    private List<Course> courses = new ArrayList<Course>();

    public UserCoursesController(CourseService courseService, AuthorService authorService, CategoryService categoryService, PriceService priceService, UserService userService) {
        this.courseService = courseService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.priceService = priceService;
        this.userService = userService;
    }

    @GetMapping
    public String showCourses(HttpServletRequest request, @RequestParam(required = false) String query) {
        List<Course> courses = query == null || query.isEmpty() ?
                this.courseService.findAll() : this.courseService.searchCourses(query);

        request.setAttribute("courses", courses);
        request.setAttribute("bodyContent", "courses");
        request.setAttribute("query", query);

//        User user = this.userService.findById(id);
//        if(user.getRoles()
//                .stream()
//                .anyMatch(s -> s.getRole().equals("SITE_USER"))){
//            return "user";
//        }
        return "user";
    }


    @GetMapping("/toCart")
    public String getCart(Model model){
        model.addAttribute("courses", courses);
        int total = 0;
        for (Course course : courses) {
            total+=course.getPrice().getPrice();
        }
        model.addAttribute("total", total);
        return "toCartUser";
    }


    @PostMapping("/{id}/toCart")
    public String addToCart(Model model, @PathVariable Long id){

        try{
            Course course = this.courseService.findById(id);
            this.courses.add(course);
            model.addAttribute("courses", courses);
            model.addAttribute("name", course.getName());
            model.addAttribute("description", course.getDescription());
            model.addAttribute("author", course.getAuthor());
            model.addAttribute("category", course.getCategory());
            model.addAttribute("price", course.getPrice());
            return "redirect:/users";
        }catch(RuntimeException ex){
            return "redirect:/users";
        }
    }


    @GetMapping("/{id}/name")
    public String getSingleCourse(Model model, @PathVariable Long id){
        try{
            Course course = this.courseService.findById(id);
//            Author author = course.getAuthor();
//            Category category = course.getCategory();
//            Price price = course.getPrice();
//            String description = course.getDescription();
//            String name = course.getName();
            model.addAttribute("course", course);
            model.addAttribute("name", course.getName());
            model.addAttribute("description", course.getDescription());
            model.addAttribute("author", course.getAuthor());
            model.addAttribute("category", course.getCategory());
            model.addAttribute("price", course.getPrice());
            return "single-courseUser";
        }catch (RuntimeException ex){
            return "redirect:/users";
        }
    }

    @PostMapping("/toCart/reset")
    public String resetPage(Model model){
        ArrayList<Course> c2 = (ArrayList<Course>) courses;
        this.courses.removeAll(c2);
        int total = 0;
        model.addAttribute("total", total);
        return "toCartUser";
    }

}
