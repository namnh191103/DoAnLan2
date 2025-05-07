package com.myapp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/user/home"})
    public String home(Model model) {
        // model.addAttribute("products", ...);
        return "user/home";
    }
} 