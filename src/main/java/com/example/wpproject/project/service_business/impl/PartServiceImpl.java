package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.model.Part;
import com.example.wpproject.project.model.exceptions.PartNotFoundException;
import com.example.wpproject.project.repository_persistence.PartRepository;
import com.example.wpproject.project.service_business.PartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }


    @Override
    public List<Part> findAll() {
        return this.partRepository.findAll();
    }

    @Override
    public Part findById(Long id) {
        return this.partRepository.findById(id).orElseThrow(PartNotFoundException::new);
    }

    @Override
    public Part save(Part part) {
        return this.partRepository.save(part);
    }

    @Override
    public Part update(Long id, Part part) {
        Part p = this.findById(id);
        p.setName(part.getName());
        p.setDescription(part.getDescription());
        return this.partRepository.save(p);
    }

    @Override
    public void deleteById(Long id) {
        this.partRepository.deleteById(id);
    }
}
