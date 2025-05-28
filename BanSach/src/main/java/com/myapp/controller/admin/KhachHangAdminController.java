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
        model.addAttribute("users", users.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        return "admin/users";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Integer id, Model model) {
        UserDTO user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user-view";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Integer id, Model model) {
        UserDTO user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user-edit";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute UserDTO userDTO, @RequestParam(value = "isAdmin", required = false) String isAdmin, Model model, @SessionAttribute("SPRING_SECURITY_CONTEXT") org.springframework.security.core.context.SecurityContext securityContext) {
        UserDTO existingUser = userService.findById(id);
        String currentEmail = securityContext.getAuthentication().getName();
        boolean isSelf = existingUser.getEmail().equalsIgnoreCase(currentEmail);
        if (existingUser.getVaiTros() != null && existingUser.getVaiTros().contains("ROLE_ADMIN")) {
            if (isSelf) {
                model.addAttribute("user", existingUser);
                model.addAttribute("error", "Không được phép sửa thông tin cá nhân hoặc bỏ quyền admin của chính mình!");
                return "admin/user-edit";
            }
            if (isAdmin == null) {
                userService.removeRoleAdmin(id);
            }
            return "redirect:/admin/users";
        }
        userService.update(id, userDTO);
        if (isAdmin != null) {
            userService.addRoleAdmin(id);
        } else {
            userService.removeRoleAdmin(id);
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/lock")
    public String lockUser(@PathVariable Integer id) {
        userService.lockUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/unlock")
    public String unlockUser(@PathVariable Integer id) {
        userService.unlockUser(id);
        return "redirect:/admin/users";
    }
} 