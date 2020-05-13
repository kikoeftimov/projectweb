package com.example.wpproject.project.web_presentation.controller;

import com.example.wpproject.project.model.dto.ChargeRequest;
import com.example.wpproject.project.service_business.AuthService;
import com.example.wpproject.project.service_business.ShoppingCartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/payments")
public class PaymentController {

    @Value("${STRIPE_P_KEY}")
    private String publicKey;

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public PaymentController(ShoppingCartService shoppingCartService,
                             AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @GetMapping("/charge")
    public String getCheckoutPage(Model model) {
        try {
            model.addAttribute("currency", "eur");
            model.addAttribute("amount", 0);
//            model.addAttribute("amount", (int) (shoppingCart.getCourses().stream().mapToDouble(c -> c.getPrice().getPrice()).sum() * 100));
            model.addAttribute("stripePublicKey", this.publicKey);
            return "toCartUser";
        } catch (RuntimeException ex) {
            return "redirect:/users" + ex.getLocalizedMessage();
        }
    }

    @PostMapping("/charge")
    public String checkout(ChargeRequest chargeRequest, Model model) {
        try {
//            ShoppingCart shoppingCart = this.shoppingCartService.checkoutShoppingCart(this.authService.getCurrentUserId(), chargeRequest);
            return "redirect:/users";
        } catch (RuntimeException ex) {
            return "redirect:/users/toCart" + ex.getLocalizedMessage();
        }
    }





}
