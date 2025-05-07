package com.myapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserAdminController {
    @GetMapping("/admin/users")
    public String users(Model model) {
        // model.addAttribute("users", ...);
        return "admin/users";
    }
} 