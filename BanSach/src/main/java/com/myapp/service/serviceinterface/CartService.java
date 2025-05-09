package com.myapp.service.serviceinterface;

import com.myapp.dto.CartItemDTO;
import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    List<CartItemDTO> getCartItems();
    CartItemDTO addToCart(CartItemDTO cartItemDTO);
    CartItemDTO updateCartItem(CartItemDTO cartItemDTO);
    void removeFromCart(Integer id);
    void clearCart();
    BigDecimal getCartTotal();
} 