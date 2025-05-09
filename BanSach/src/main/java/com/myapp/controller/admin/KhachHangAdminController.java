package com.myapp.controller.admin;

import com.myapp.dto.UserDTO;
import com.myapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class KhachHangAdminController {
    private final UserService userService;

    @GetMapping
    public String listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<UserDTO> users = userService.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        return "admin/users/list";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Integer id, Model model) {
        UserDTO user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/users/view";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Integer id, Model model) {
        UserDTO user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/users/edit";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute UserDTO userDTO) {
        userService.update(id, userDTO);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
} 