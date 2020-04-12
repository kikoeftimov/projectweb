package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.Price;
import com.example.wpproject.project.model.User;

import java.util.List;

public interface PriceService {
    List<Price> findAll();
    Price findById(Long id);
    Price save(Price price);
    Price update(Long id, Price price);
    void deleteById(Long id);
}
