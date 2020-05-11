package com.example.wpproject.project.web_presentation.controller;

import com.example.wpproject.project.model.Order;
import com.example.wpproject.project.service_business.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/paypal")
public class PaypalController {

    private final PaypalService paypalService;

    public PaypalController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    public static final String SUCCESS_URL = "paypal/pay/success";
    public static final String CANCEL_URL = "paypal/pay/cancel";

    @GetMapping
    public String paypal(){
        return "paypal";
    }

    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") Order order){
        try {
            Payment payment = paypalService.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    "http://localhost:8080/" + CANCEL_URL,
                    "http://localhost:8080/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/paypal";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/paypal";
    }

}
