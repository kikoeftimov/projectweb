package com.example.wpproject.project.web_presentation.rest_controller;

import com.example.wpproject.project.model.Course;
import com.example.wpproject.project.service_business.CourseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

    private final CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> findAll(){
        return this.courseService.findAll();
    }

    @GetMapping(params = "name")
    public List<Course> findAllByName(String name){
        return this.courseService.findAllByName(name);
    }

    @GetMapping("/{id}")
    public Course findCourseById(@PathVariable Long id){
        return this.courseService.findById(id);
    }


    @PostMapping
    public Course saveCourse(Course course, @RequestParam(required = false) MultipartFile image) throws IOException{
        return this.courseService.save(course, image);
    }

    @PutMapping("/{id}")
    public Course updateCourse(Long id, Course course, @RequestParam(required = false) MultipartFile image) throws IOException{
        return this.courseService.update(id,course,image);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id){
        this.courseService.deleteById(id);
    }


    @GetMapping(params = "term")
    public List<Course> searchCourses(@RequestParam String term){
        return courseService.searchCourses(term);
    }
}
