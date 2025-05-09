package com.myapp.service.impl;

import com.myapp.dto.CartItemDTO;
import com.myapp.dto.UserDTO;
import com.myapp.dto.ProductDTO;
import com.myapp.model.Cart;
import com.myapp.model.User;
import com.myapp.model.Product;
import com.myapp.repository.CartRepository;
import com.myapp.service.CartService;
import com.myapp.service.UserService;
import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public List<CartItemDTO> getCartItems() {
        return cartRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartItemDTO addToCart(CartItemDTO cartItemDTO) {
        User user = userService.findEntityByEmail(cartItemDTO.getUserEmail());
        Product product = productService.findEntityById(cartItemDTO.getTacPhamId());
        
        Cart existingCart = cartRepository.findByUserAndProduct(user, product);
        if (existingCart != null) {
            existingCart.setSoLuong(existingCart.getSoLuong() + cartItemDTO.getSoLuong());
            return convertToDTO(cartRepository.save(existingCart));
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setSoLuong(cartItemDTO.getSoLuong());
            return convertToDTO(cartRepository.save(cart));
        }
    }

    @Override
    @Transactional
    public CartItemDTO updateCartItem(CartItemDTO cartItemDTO) {
        Cart cart = cartRepository.findById(cartItemDTO.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.setSoLuong(cartItemDTO.getSoLuong());
        return convertToDTO(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public void removeFromCart(Integer id) {
        cartRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void clearCart() {
        cartRepository.deleteAll();
    }

    @Override
    public BigDecimal getCartTotal() {
        return cartRepository.findAll().stream()
                .map(item -> item.getProduct().getGiaBan()
                        .multiply(BigDecimal.valueOf(item.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Cart> getCartByUser(UserDTO userDTO) {
        User user = userService.findEntityByEmail(userDTO.getEmail());
        return cartRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void addToCart(UserDTO userDTO, ProductDTO productDTO, Integer quantity) {
        User user = userService.findEntityByEmail(userDTO.getEmail());
        Product product = productService.findEntityById(productDTO.getId());
        
        Cart existingCart = cartRepository.findByUserAndProduct(user, product);
        if (existingCart != null) {
            existingCart.setSoLuong(existingCart.getSoLuong() + quantity);
            cartRepository.save(existingCart);
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setSoLuong(quantity);
            cartRepository.save(cart);
        }
    }

    @Override
    @Transactional
    public void updateCartQuantity(Integer id, Integer quantity) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.setSoLuong(quantity);
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal calculateTotal(List<Cart> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProduct().getGiaBan()
                        .multiply(BigDecimal.valueOf(item.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private CartItemDTO convertToDTO(Cart cart) {
        return CartItemDTO.builder()
                .id(cart.getId())
                .tacPhamId(cart.getProduct().getId())
                .tuaDe(cart.getProduct().getTuaDe())
                .giaBan(cart.getProduct().getGiaBan())
                .phanTramGiamGia(cart.getProduct().getPhanTramGiamGia())
                .soLuong(cart.getSoLuong())
                .anhBia(cart.getProduct().getAnhBia())
                .thanhTien(cart.getProduct().getGiaBan()
                        .multiply(BigDecimal.valueOf(cart.getSoLuong())))
                .build();
    }
} 