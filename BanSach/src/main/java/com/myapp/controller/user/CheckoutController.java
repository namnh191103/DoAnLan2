package com.myapp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {
    @GetMapping("/user/checkout")
    public String checkout(Model model) {
        // model.addAttribute("user", ...);
        return "user/checkout";
    }
} 