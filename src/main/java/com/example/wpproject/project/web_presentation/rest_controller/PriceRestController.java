package com.example.wpproject.project.web_presentation.rest_controller;

import com.example.wpproject.project.model.Category;
import com.example.wpproject.project.model.Price;
import com.example.wpproject.project.service_business.CategoryService;
import com.example.wpproject.project.service_business.PriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class PriceRestController {

    private final PriceService priceService;

    public PriceRestController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public List<Price> findAllPrices(){
        return this.priceService.findAll();
    }

    @GetMapping("/{id}")
    public Price getPriceById(@PathVariable Long id){
        return this.priceService.findById(id);
    }

    @PostMapping
    public Price savePrice(Price price){
        return this.priceService.save(price);
    }

    @PutMapping("/{id}")
    public Price update(@PathVariable Long id, Price price){
        return this.priceService.update(id,price);
    }

    @DeleteMapping("/{id}")
    public void deletePrice(@PathVariable Long id){
        this.priceService.deleteById(id);
    }
}

