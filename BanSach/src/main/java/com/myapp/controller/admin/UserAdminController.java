package com.myapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller xử lý các request liên quan đến quản lý người dùng cho admin
 * 
 * Sử dụng annotation @Controller để đánh dấu đây là một Spring MVC Controller
 */
@Controller
public class UserAdminController {
    /**
     * Xử lý request GET để hiển thị trang quản lý người dùng
     * 
     * @param model Model để truyền dữ liệu đến view
     * @return Tên view template "admin/users"
     * 
     * URL mapping: "/admin/user-management"
     * Chức năng:
     * - Hiển thị trang quản lý người dùng
     * - Chuẩn bị dữ liệu để hiển thị danh sách người dùng
     */
    @GetMapping("/admin/user-management")
    public String users(Model model) {
        // model.addAttribute("users", ...);
        return "admin/users";
    }
} 