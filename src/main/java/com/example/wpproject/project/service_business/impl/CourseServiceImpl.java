package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.model.*;
import com.example.wpproject.project.model.exceptions.CourseNotFoundException;
import com.example.wpproject.project.repository_persistence.CourseRepository;
import com.example.wpproject.project.service_business.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final PartService partService;
    private final PriceService priceService;

    public CourseServiceImpl(CourseRepository courseRepository, CategoryService categoryService, AuthorService authorService, PartService partService, PriceService priceService) {
        this.courseRepository = courseRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.partService = partService;
        this.priceService = priceService;
    }


    @Override
    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public List<Course> findAllByName(String name) {
        return this.courseRepository.findAllByName(name);
    }

    @Override
    public Course findById(Long id) {
        return this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public Course save(Course course, MultipartFile image) throws IOException {
        Category category = this.categoryService.findById(course.getCategory().getId());
        Author author = this.authorService.findById(course.getAuthor().getId());
        Price price = this.priceService.findById(course.getPrice().getId());

        course.setCategory(category);
        course.setAuthor(author);
        course.setPrice(price);

        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            course.setImageBase64(base64Image);
        }

        return this.courseRepository.save(course);
    }

    @Override
    public Course update(Long id, Course course, MultipartFile image) throws IOException {
        Course c = this.findById(id);

        Category category = this.categoryService.findById(course.getCategory().getId());
        Author author = this.authorService.findById(course.getAuthor().getId());
        Price price = this.priceService.findById(course.getPrice().getId());

        c.setCategory(category);
        c.setAuthor(author);
        c.setPrice(price);

        c.setDescription(course.getDescription());
        c.setPart(course.getPart());

        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            c.setImageBase64(base64Image);
        }
        return this.courseRepository.save(c);
    }

    @Override
    public void deleteById(Long id) {
        this.courseRepository.deleteById(id);
    }

    @Override
    public List<Course> searchCourses(String term) {
        return this.courseRepository.searchCourses(term);
    }
}
