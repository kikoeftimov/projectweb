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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    private final CourseService courseService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PriceService priceService;
    private List<Course> courses = new ArrayList<Course>();


    public CoursesController(CourseService courseService, AuthorService authorService, CategoryService categoryService, PriceService priceService) {
        this.courseService = courseService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.priceService = priceService;
    }

//    @GetMapping
//    public String getCoursesPage(Model model){
//        List<Course> courses = this.courseService.findAll();
//        model.addAttribute("courses", courses);
//        return "courses";
//    }

    @GetMapping
    public String showCourses(HttpServletRequest request, @RequestParam(required = false) String query){
        List<Course> courses = query == null || query.isEmpty() ?
                this.courseService.findAll() : this.courseService.searchCourses(query);

        request.setAttribute("courses", courses);
        request.setAttribute("bodyContent", "courses");
        request.setAttribute("query", query);
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


    @GetMapping("/toCart")
    public String getCart(Model model){
        model.addAttribute("courses", courses);
        int total = 0;
        for (Course course : courses) {
            total+=course.getPrice().getPrice();
        }
        model.addAttribute("total", total);
        return "toCart";
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
            return "redirect:/courses";
        }catch(RuntimeException ex){
            return "redirect:/courses";
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

    @DeleteMapping("/toCart/{id}/delete")
    public String deleteCourseFromCartWithDelete(@PathVariable Long id){
        try{
            Course course = this.courseService.findById(id);
            this.courses.remove(course);
            return "toCart";
        }
        catch(RuntimeException ex){
            return "redirect:/courses";
        }
    }

    @PostMapping("/toCart/{id}/delete")
    public String deleteCourseFromCart(@PathVariable Long id){

        try{
            Course course = this.courseService.findById(id);
            ArrayList<Course> c2 = (ArrayList<Course>) courses;
            c2.remove(course);
            return "toCart";
        }
        catch(RuntimeException ex){
            return "redirect:/courses";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteWithPost(@PathVariable Long id){
        this.courseService.deleteById(id);
        return "redirect:/courses";
    }


    @PostMapping("/toCart/reset")
    public String resetPage(Model model){
        ArrayList<Course> c2 = (ArrayList<Course>) courses;
        this.courses.removeAll(c2);
        int total = 0;
        model.addAttribute("total", total);
        return "toCart";
    }
}
