package com.example.wpproject.project.web_presentation.rest_controller;

import com.example.wpproject.project.model.ShoppingCart;
import com.example.wpproject.project.service_business.AuthService;
import com.example.wpproject.project.service_business.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartRestController {


    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public ShoppingCartRestController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }


    @GetMapping
    public List<ShoppingCart> findAllCardsForUser(){
        return this.shoppingCartService.findAllByUsername(this.authService.getCurrentUserId());
    }


    @PostMapping
    public ShoppingCart createShoppingCart(){
        return this.shoppingCartService.createNewShoppingCart(this.authService.getCurrentUserId());
    }


    @PatchMapping("/{courseId}/products")
    public ShoppingCart addCourseToCard(@PathVariable Long courseId){
        return this.shoppingCartService.addCourseToShoppingCart
                (this.authService.getCurrentUserId(), courseId);
    }


    @DeleteMapping("/{courseId}/products")
    public ShoppingCart deleteCourseFromCard(@PathVariable Long courseId){
        return this.shoppingCartService.removeCourseFromShoppingCart
                (this.authService.getCurrentUserId(), courseId);
    }


    @PatchMapping("/cancel")
    public ShoppingCart cancelShoppingCard(){
        return this.shoppingCartService.cancelShoppingCart(this.authService.getCurrentUserId());
    }

//    @PatchMapping("/checkout")
//    public ShoppingCart checkoutShoppingCard(){
//        return this.shoppingCartService.checkoutShoppingCart(this.authService.getCurrentUserId());
//    }

}