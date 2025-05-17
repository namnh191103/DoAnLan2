package com.myapp.controller.user;

import com.myapp.dto.UserDTO;
import com.myapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller xử lý các request liên quan đến tài khoản người dùng
 * 
 * Sử dụng annotation @Controller để đánh dấu đây là một Spring MVC Controller
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 * @RequestMapping("/user/account"): Tất cả các request trong controller này đều bắt đầu bằng "/user/account"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/account")
public class UserAccountController {
    /**
     * Service xử lý logic liên quan đến người dùng
     * Được inject thông qua constructor
     */
    private final UserService userService;

    /**
     * Xử lý request GET cho trang thông tin tài khoản
     * 
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @param model Model để truyền dữ liệu đến view
     * @return Tên view template "user/account"
     * 
     * URL mapping: "/user/account"
     * Chức năng:
     * - Lấy email của người dùng đang đăng nhập
     * - Tìm thông tin người dùng từ database
     * - Thêm thông tin vào model để hiển thị trên view
     */
    @GetMapping
    public String accountPage(Authentication authentication, Model model) {
        String email = authentication.getName();
        UserDTO user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "user/account";
    }

    /**
     * Xử lý request POST cho việc đổi mật khẩu
     * 
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @param oldPassword Mật khẩu cũ
     * @param newPassword Mật khẩu mới
     * @param model Model để truyền dữ liệu đến view
     * @return Tên view template "user/account"
     * 
     * URL mapping: "/user/account/change-password"
     * Chức năng:
     * - Lấy email của người dùng đang đăng nhập
     * - Gọi service để thực hiện đổi mật khẩu
     * - Xử lý các trường hợp thành công/thất bại
     * - Thêm thông báo và thông tin người dùng vào model
     */
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

    /**
     * Xử lý request POST cho việc cập nhật số điện thoại
     * 
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @param soDienThoai Số điện thoại mới
     * @param model Model để truyền dữ liệu đến view
     * @return Tên view template "user/account"
     * 
     * URL mapping: "/user/account/update-phone"
     * Chức năng:
     * - Lấy email của người dùng đang đăng nhập
     * - Gọi service để cập nhật số điện thoại
     * - Xử lý các trường hợp thành công/thất bại
     * - Thêm thông báo và thông tin người dùng vào model
     */
    @PostMapping("/update-phone")
    public String updatePhone(
            Authentication authentication,
            @RequestParam("soDienThoai") String soDienThoai,
            Model model
    ) {
        String email = authentication.getName();
        try {
            UserDTO user = userService.findByEmail(email);
            user.setSoDienThoai(soDienThoai);
            userService.update(user.getId(), user);
            model.addAttribute("success", "Cập nhật số điện thoại thành công!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        UserDTO user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "user/account";
    }
} 