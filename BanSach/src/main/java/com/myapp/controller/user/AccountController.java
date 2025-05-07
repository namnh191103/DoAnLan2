package com.myapp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    @GetMapping("/user/account")
    public String account(Model model) {
        // model.addAttribute("user", ...);
        // model.addAttribute("orders", ...);
        return "user/account";
    }
} 