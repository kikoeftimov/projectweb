package com.example.wpproject.project.service_business.impl;

import com.example.wpproject.project.service_business.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Override
    public void pay(float price) {
        System.out.println("Charging from user: username");
    }
}
