package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.dto.ChargeRequest;
import com.example.wpproject.project.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart findActiveShoppingCartByUserId(Integer userId);

    ShoppingCart getActiveShoppingCart(Integer userId);

    List<ShoppingCart> findAllByUsername(Integer userId);

    ShoppingCart createNewShoppingCart(Integer userId);

    ShoppingCart addCourseToShoppingCart(Integer userId, Long courseId);

    ShoppingCart removeCourseFromShoppingCart(Integer userId, Long courseId);

    ShoppingCart cancelShoppingCart(Integer userId);

    ShoppingCart checkoutShoppingCart(Integer userId, ChargeRequest chargeRequest);
}
