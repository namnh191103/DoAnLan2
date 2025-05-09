package com.myapp.controller.user;

import com.myapp.dto.UserDTO;
import com.myapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/account")
public class UserAccountController {
    private final UserService userService;

    @GetMapping
    public String accountPage(Authentication authentication, Model model) {
        String email = authentication.getName();
        UserDTO user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "user/account";
    }

    @PostMapping("/change-password")
    public String changePassword(
            Authentication authentication,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            Model model
    ) {
        String email = authentication.getName();
        try {
            userService.changePassword(email, oldPassword, newPassword);
            model.addAttribute("success", "Đổi mật khẩu thành công!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        UserDTO user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "user/account";
    }
} 