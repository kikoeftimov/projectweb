package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.model.Price;
import com.example.wpproject.project.model.exceptions.PriceNotFoundException;
import com.example.wpproject.project.repository_persistence.PriceRepository;
import com.example.wpproject.project.service_business.PriceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }


    @Override
    public List<Price> findAll() {
        return this.priceRepository.findAll();
    }

    @Override
    public Price findById(Long id) {
        return this.priceRepository.findById(id).orElseThrow(PriceNotFoundException::new);
    }

    @Override
    public Price save(Price price) {
        return this.priceRepository.save(price);
    }

    @Override
    public Price update(Long id, Price price) {
        Price p = this.findById(id);
        p.setPrice(price.getPrice());
        return this.priceRepository.save(p);
    }

    @Override
    public void deleteById(Long id) {
        this.priceRepository.deleteById(id);
    }
}
