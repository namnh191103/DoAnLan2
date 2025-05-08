package com.myapp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private com.myapp.service.serviceinterface.KhachHangService khachHangService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register() {
        return "user/register";
    }

    @PostMapping("/do-login")
    public String doLogin(
            @RequestParam("username") String email,
            @RequestParam("password") String matKhau,
            Model model,
            HttpSession session
    ) {
        var user = khachHangService.login(email, matKhau);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            return "user/login";
        }
    }
} 