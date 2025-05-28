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
 * Bao gồm đăng nhập, đăng ký, quên mật khẩu
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

    /**
     * Hiển thị form quên mật khẩu
     */
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "user/forgot-password";
    }

    /**
     * Xử lý gửi OTP về email
     */
    @PostMapping("/forgot-password")
    public String sendOtp(@RequestParam("email") String email, Model model) {
        try {
            userService.sendPasswordResetOtp(email);
            model.addAttribute("email", email);
            model.addAttribute("success", "Đã gửi mã OTP về email. Vui lòng kiểm tra hộp thư!");
            return "user/enter-otp";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/forgot-password";
        }
    }

    /**
     * Hiển thị form nhập OTP và mật khẩu mới
     */
    @GetMapping("/enter-otp")
    public String enterOtpPage(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "user/enter-otp";
    }

    /**
     * Xử lý xác nhận OTP và đổi mật khẩu
     */
    @PostMapping("/reset-password")
    public String resetPasswordWithOtp(@RequestParam("email") String email,
                                       @RequestParam("otp") String otp,
                                       @RequestParam("newPassword") String newPassword,
                                       Model model) {
        try {
            userService.resetPasswordWithOtp(email, otp, newPassword);
            return "redirect:/login?resetSuccess=true";
        } catch (Exception e) {
            model.addAttribute("email", email);
            model.addAttribute("error", e.getMessage());
            return "user/enter-otp";
        }
    }
} 