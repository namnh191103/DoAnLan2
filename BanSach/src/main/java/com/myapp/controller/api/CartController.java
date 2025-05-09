package com.myapp.controller.api;

import com.myapp.dto.CartItemDTO;
import com.myapp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCartItems() {
        return ResponseEntity.ok(cartService.getCartItems());
    }

    @PostMapping("/items")
    public ResponseEntity<CartItemDTO> addToCart(@RequestBody CartItemDTO cartItemDTO) {
        return ResponseEntity.ok(cartService.addToCart(cartItemDTO));
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @PathVariable Integer id,
            @RequestBody CartItemDTO cartItemDTO) {
        cartItemDTO.setId(id);
        return ResponseEntity.ok(cartService.updateCartItem(cartItemDTO));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Integer id) {
        cartService.removeFromCart(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getCartTotal() {
        return ResponseEntity.ok(cartService.getCartTotal());
    }
} 