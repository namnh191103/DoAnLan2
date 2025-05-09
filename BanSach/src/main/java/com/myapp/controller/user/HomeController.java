package com.myapp.controller.user;

import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;

    @GetMapping({"/", "/user/home"})
    public String home(Model model) {
        var pageable = PageRequest.of(0, 12, Sort.by("id").descending());
        var products = productService.findAll(pageable);
        model.addAttribute("products", products);
        return "user/home";
    }
} 