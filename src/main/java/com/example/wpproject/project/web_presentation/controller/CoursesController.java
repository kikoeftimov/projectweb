package com.example.wpproject.project.web_presentation.controller;

import com.example.wpproject.project.model.Author;
import com.example.wpproject.project.model.Category;
import com.example.wpproject.project.model.Course;
import com.example.wpproject.project.model.Price;
import com.example.wpproject.project.service_business.AuthorService;
import com.example.wpproject.project.service_business.CategoryService;
import com.example.wpproject.project.service_business.CourseService;
import com.example.wpproject.project.service_business.PriceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    private final CourseService courseService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PriceService priceService;

    public CoursesController(CourseService courseService, AuthorService authorService, CategoryService categoryService, PriceService priceService) {
        this.courseService = courseService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.priceService = priceService;
    }

    @GetMapping
    public String getCoursesPage(Model model){
        List<Course> courses = this.courseService.findAll();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/new")
    public String addCourse(Model model){
    List<Author> authors = this.authorService.findAll();
    List<Category> categories = this.categoryService.findAll();
    List<Price> prices = this.priceService.findAll();
    model.addAttribute("authors", authors);
    model.addAttribute("categories", categories);
    model.addAttribute("prices", prices);
    model.addAttribute("course", new Course());
    return "add-course";
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
            return "single-course";
        }catch (RuntimeException ex){
            return "redirect:/courses";
        }
    }

    @GetMapping("/{id}/edit")
    public String editCourse(Model model, @PathVariable Long id){
        try{
            Course course = this.courseService.findById(id);
            List<Author> authors = this.authorService.findAll();
            List<Category> categories = this.categoryService.findAll();
            List<Price> prices = this.priceService.findAll();
            model.addAttribute("course", course);
            model.addAttribute("authors", authors);
            model.addAttribute("categories", categories);
            model.addAttribute("prices", prices);
            return "add-course";
        }
        catch (RuntimeException ex) {
            return "redirect:/courses";
        }
    }

    @PostMapping
    public String saveCourse(Course course,
                             BindingResult bindingResult,
                             @RequestParam MultipartFile image,
                             Model model) throws IOException{
        if(bindingResult.hasErrors()){
            List<Author> authors = this.authorService.findAll();
            List<Category> categories = this.categoryService.findAll();
            List<Price> prices = this.priceService.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("categories", categories);
            model.addAttribute("prices", prices);
            return "add-course";
        }
        try {
            this.courseService.save(course, image);
        } catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/courses";
    }


    @DeleteMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id){
        this.courseService.deleteById(id);
        return "redirect:/courses";
    }

    @PostMapping("/{id}/delete")
    public String deleteWithPost(@PathVariable Long id){
        this.courseService.deleteById(id);
        return "redirect:/courses";
    }
}
