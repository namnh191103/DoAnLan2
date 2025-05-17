package com.myapp.controller.admin;

import com.myapp.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Controller
public class DashboardController {
    private final DonHangService donHangService;
    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        var stats = donHangService.getDashboardStats();
        System.out.println("[DASHBOARD] tongDoanhThu: " + stats.getTongDoanhThu());
        System.out.println("[DASHBOARD] tongSoDon: " + stats.getTongSoDon());
        System.out.println("[DASHBOARD] soDonHoanThanh: " + stats.getSoDonHoanThanh());
        System.out.println("[DASHBOARD] doanhThu7Ngay: " + stats.getDoanhThu7Ngay());
        model.addAttribute("tongDoanhThu", stats.getTongDoanhThu());
        model.addAttribute("tongSoDon", stats.getTongSoDon());
        model.addAttribute("soDonHoanThanh", stats.getSoDonHoanThanh());
        try {
            model.addAttribute("doanhThu7NgayJson", new ObjectMapper().writeValueAsString(stats.getDoanhThu7Ngay()));
        } catch (Exception e) {
            model.addAttribute("doanhThu7NgayJson", "[]");
        }
        return "admin/dashboard";
    }
} 