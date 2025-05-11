package com.myapp.controller.user;

import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller xử lý các request liên quan đến trang chủ
 * Sử dụng annotation @Controller để đánh dấu đây là một Spring MVC Controller
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 */
@Controller
@RequiredArgsConstructor
public class HomeController {
    /**
     * Service xử lý logic liên quan đến sản phẩm
     * Được inject thông qua constructor
     */
    private final ProductService productService;

    /**
     * Xử lý request GET cho trang chủ
     * 
     * @param model Model để truyền dữ liệu đến view
     * @return Tên view template "user/home"
     * 
     * URL mapping:
     * - "/": Trang chủ mặc định
     * - "/user/home": Trang chủ cho người dùng
     * 
     * Chức năng:
     * - Lấy 12 sản phẩm mới nhất (sắp xếp theo ID giảm dần)
     * - Thêm danh sách sản phẩm vào model để hiển thị trên view
     */
    @GetMapping({"/", "/user/home"})
    public String home(Model model) {
        var pageable = PageRequest.of(0, 12, Sort.by("id").descending());
        var products = productService.findAll(pageable);
        model.addAttribute("products", products);
        return "user/home";
    }
} 