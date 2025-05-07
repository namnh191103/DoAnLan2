package com.myapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductAdminController {
    @GetMapping("/admin/products")
    public String products(Model model) {
        // model.addAttribute("products", ...);
        return "admin/products";
    }
} 