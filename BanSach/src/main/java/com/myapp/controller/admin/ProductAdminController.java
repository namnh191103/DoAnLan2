package com.myapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import com.myapp.dto.ProductDTO;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
public class ProductAdminController {
    private final ProductService productService;
    @GetMapping("/admin/products")
    public String products(Model model) {
        List<ProductDTO> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products";
    }

    @GetMapping("/admin/products/add")
    public String showAddForm(Model model) {
        model.addAttribute("title", "Thêm sản phẩm");
        model.addAttribute("action", "/admin/products/add");
        model.addAttribute("product", new ProductDTO());
        return "admin/product-form";
    }

    @PostMapping("/admin/products/add")
    public String addProduct(@ModelAttribute("product") ProductDTO productDTO,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (productDTO.getTuaDe() == null || productDTO.getTuaDe().trim().isEmpty()) {
            model.addAttribute("error", "Tiêu đề không được để trống!");
            model.addAttribute("title", "Thêm sản phẩm");
            model.addAttribute("action", "/admin/products/add");
            return "admin/product-form";
        }
        if (productDTO.getGiaBan() == null || productDTO.getGiaBan().doubleValue() < 0) {
            model.addAttribute("error", "Giá bán phải lớn hơn hoặc bằng 0!");
            model.addAttribute("title", "Thêm sản phẩm");
            model.addAttribute("action", "/admin/products/add");
            return "admin/product-form";
        }
        try {
            productService.create(productDTO);
            redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công!");
            return "redirect:/admin/products";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("title", "Thêm sản phẩm");
            model.addAttribute("action", "/admin/products/add");
            return "admin/product-form";
        }
    }

    @GetMapping("/admin/products/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ProductDTO product = productService.findById(id);
            model.addAttribute("title", "Sửa sản phẩm");
            model.addAttribute("action", "/admin/products/edit/" + id);
            model.addAttribute("product", product);
            return "admin/product-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm!");
            return "redirect:/admin/products";
        }
    }

    @PostMapping("/admin/products/edit/{id}")
    public String editProduct(@PathVariable Integer id,
                             @ModelAttribute("product") ProductDTO productDTO,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (productDTO.getTuaDe() == null || productDTO.getTuaDe().trim().isEmpty()) {
            model.addAttribute("error", "Tiêu đề không được để trống!");
            model.addAttribute("title", "Sửa sản phẩm");
            model.addAttribute("action", "/admin/products/edit/" + id);
            return "admin/product-form";
        }
        if (productDTO.getGiaBan() == null || productDTO.getGiaBan().doubleValue() < 0) {
            model.addAttribute("error", "Giá bán phải lớn hơn hoặc bằng 0!");
            model.addAttribute("title", "Sửa sản phẩm");
            model.addAttribute("action", "/admin/products/edit/" + id);
            return "admin/product-form";
        }
        try {
            productService.update(id, productDTO);
            redirectAttributes.addFlashAttribute("success", "Cập nhật sản phẩm thành công!");
            return "redirect:/admin/products";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("title", "Sửa sản phẩm");
            model.addAttribute("action", "/admin/products/edit/" + id);
            return "admin/product-form";
        }
    }

    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            productService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Xóa sản phẩm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa sản phẩm: " + e.getMessage());
        }
        return "redirect:/admin/products";
    }
} 