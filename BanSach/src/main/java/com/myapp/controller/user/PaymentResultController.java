package com.myapp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentResultController {
    @GetMapping("/user/payment-success")
    public String paymentSuccess() {
        return "user/payment-success";
    }

    @GetMapping("/user/payment-fail")
    public String paymentFail() {
        return "user/payment-fail";
    }
} 