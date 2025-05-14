package com.myapp.controller.admin;

import com.myapp.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.myapp.dto.DonHangDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class OrderAdminController {
    private final DonHangService donHangService;

    @GetMapping("/admin/orders")
    public String orders(Model model) {
        model.addAttribute("orders", donHangService.findAll(org.springframework.data.domain.Pageable.unpaged()).getContent());
        return "admin/orders";
    }

    @GetMapping("/admin/orders/edit/{id}")
    public String editOrder(@PathVariable Integer id, Model model) {
        DonHangDTO order = donHangService.findById(id);
        model.addAttribute("order", order);
        return "admin/order-edit";
    }

    @PostMapping("/admin/orders/edit/{id}")
    public String updateOrder(@PathVariable Integer id, @ModelAttribute("order") DonHangDTO orderDTO) {
        donHangService.update(id, orderDTO);
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders/confirm/{id}")
    public String confirmOrder(@PathVariable Integer id) {
        DonHangDTO order = donHangService.findById(id);
        if (order != null && !"ĐÃ_GIAO".equals(order.getTrangThai())) {
            order.setTrangThai("ĐÃ_GIAO");
            donHangService.update(id, order);
        }
        return "redirect:/admin/orders";
    }
} 