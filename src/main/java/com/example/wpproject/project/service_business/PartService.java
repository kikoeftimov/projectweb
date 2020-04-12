package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.Part;
import com.example.wpproject.project.model.User;

import java.util.List;

public interface PartService {

    List<Part> findAll();
    Part findById(Long id);
    Part save(Part part);
    Part update(Long id, Part part);
    void deleteById(Long id);

}
