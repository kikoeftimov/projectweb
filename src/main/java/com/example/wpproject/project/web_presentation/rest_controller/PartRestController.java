package com.example.wpproject.project.web_presentation.rest_controller;

import com.example.wpproject.project.model.Part;
import com.example.wpproject.project.service_business.PartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartRestController {

    private final PartService partService;


    public PartRestController(PartService partService) {
        this.partService = partService;
    }


    @GetMapping
    public List<Part> findAllParts(){
        return this.partService.findAll();
    }

    @GetMapping("/{id}")
    public Part findPartById(@PathVariable Long id){
        return this.partService.findById(id);
    }

    @PostMapping
    public Part savePart(Part part){
        return this.partService.save(part);
    }

    @PutMapping("/{id}")
    public Part update(@PathVariable Long id, Part part){
        return this.partService.update(id,part);
    }

    @DeleteMapping("/{id}")
    public void deletePart(@PathVariable Long id){
        this.partService.deleteById(id);
    }
}
