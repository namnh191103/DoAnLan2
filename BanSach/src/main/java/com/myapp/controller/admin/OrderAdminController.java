package com.myapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderAdminController {
    @GetMapping("/admin/orders")
    public String orders(Model model) {
        // model.addAttribute("orders", ...);
        return "admin/orders";
    }
} 