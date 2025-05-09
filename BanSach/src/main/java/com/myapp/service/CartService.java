package com.myapp.service;

import com.myapp.dto.CartItemDTO;
import com.myapp.dto.UserDTO;
import com.myapp.dto.ProductDTO;
import com.myapp.model.Cart;
import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    List<CartItemDTO> getCartItems();
    CartItemDTO addToCart(CartItemDTO cartItemDTO);
    CartItemDTO updateCartItem(CartItemDTO cartItemDTO);
    void removeFromCart(Integer id);
    void clearCart();
    BigDecimal getCartTotal();
    List<Cart> getCartByUser(UserDTO userDTO);
    void addToCart(UserDTO userDTO, ProductDTO productDTO, Integer quantity);
    void updateCartQuantity(Integer id, Integer quantity);
    BigDecimal calculateTotal(List<Cart> cartItems);
} 