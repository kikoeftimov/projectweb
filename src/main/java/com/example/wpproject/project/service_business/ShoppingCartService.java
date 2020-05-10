package com.example.wpproject.project.service_business;

import com.example.wpproject.project.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart getActiveShoppingCart(String username);

    List<ShoppingCart> findAllByUsername(String username);

    ShoppingCart createNewShoppingCart(String username);

    ShoppingCart addCourseToShoppingCart(String username, Long courseId);

    ShoppingCart removeCourseFromShoppingCart(String username, Long courseId);

    ShoppingCart cancelShoppingCart(String username);

    ShoppingCart checkoutShoppingCart(String username);
}
