package com.myapp.controller.user;

import com.myapp.dto.UserDTO;
import com.myapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller xử lý các request liên quan đến xác thực người dùng
 * Bao gồm đăng nhập và đăng ký
 * 
 * Sử dụng annotation @Controller để đánh dấu đây là một Spring MVC Controller
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 */
@Controller
@RequiredArgsConstructor
public class AuthController {
    /**
     * Service xử lý logic liên quan đến người dùng
     * Được inject thông qua constructor
     */
    private final UserService userService;

    /**
     * Xử lý request GET cho trang đăng nhập
     * 
     * @return Tên view template "user/login"
     * 
     * URL mapping: "/login"
     * Chức năng: Hiển thị form đăng nhập
     */
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    /**
     * Xử lý request GET cho trang đăng ký
     * 
     * @param model Model để truyền dữ liệu đến view
     * @return Tên view template "user/register"
     * 
     * URL mapping: "/register"
     * Chức năng: 
     * - Tạo một đối tượng UserDTO mới
     * - Thêm vào model để hiển thị form đăng ký
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user/register";
    }

    /**
     * Xử lý request POST cho việc đăng ký
     * 
     * @param user Dữ liệu người dùng từ form đăng ký
     * @param model Model để truyền dữ liệu đến view
     * @return 
     * - Nếu thành công: Chuyển hướng đến trang đăng nhập với thông báo đăng ký thành công
     * - Nếu thất bại: Hiển thị lại form đăng ký với thông báo lỗi
     * 
     * URL mapping: "/do-register"
     * Chức năng:
     * - Xử lý dữ liệu đăng ký từ form
     * - Gọi service để thực hiện đăng ký
     * - Xử lý các trường hợp lỗi và hiển thị thông báo phù hợp
     */
    @PostMapping("/do-register")
    public String doRegister(@ModelAttribute UserDTO user, Model model) {
        try {
            userService.register(user);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "user/register";
        }
    }
} 