package com.myapp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {
    @GetMapping("/user/products/{id}")
    public String productDetail(@PathVariable Integer id, Model model) {
        // model.addAttribute("product", ...);
        return "user/product-detail";
    }
} 