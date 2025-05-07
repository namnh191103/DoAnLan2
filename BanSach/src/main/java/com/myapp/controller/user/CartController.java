package com.myapp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @GetMapping("/user/cart")
    public String cart(Model model) {
        // model.addAttribute("cartItems", ...);
        // model.addAttribute("cartTotal", ...);
        return "user/cart";
    }
} 