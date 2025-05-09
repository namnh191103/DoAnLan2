package com.myapp.controller.user;

import com.myapp.dto.UserDTO;
import com.myapp.dto.ProductDTO;
import com.myapp.model.Cart;
import com.myapp.model.User;
import com.myapp.model.Product;
import com.myapp.service.CartService;
import com.myapp.service.UserService;
import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class UserCartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public String viewCart(Model model, Authentication authentication) {
        String email = authentication.getName();
        UserDTO userDTO = userService.findByEmail(email);
        List<Cart> cartItems = cartService.getCartByUser(userDTO);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.calculateTotal(cartItems));
        return "user/cart";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addToCart(@RequestParam Integer productId,
                          @RequestParam Integer quantity,
                          Authentication authentication) {
        try {
            String email = authentication.getName();
            UserDTO userDTO = userService.findByEmail(email);
            ProductDTO productDTO = productService.findById(productId);
            cartService.addToCart(userDTO, productDTO, quantity);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @PostMapping("/remove/{id}")
    @ResponseBody
    public String removeFromCart(@PathVariable Integer id) {
        try {
            cartService.removeFromCart(id);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public String updateCartQuantity(@PathVariable Integer id,
                                   @RequestParam Integer quantity) {
        try {
            cartService.updateCartQuantity(id, quantity);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
} 