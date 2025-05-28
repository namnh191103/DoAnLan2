package com.myapp.service;

import com.myapp.dto.CartItemDTO;
import com.myapp.dto.UserDTO;
import com.myapp.dto.ProductDTO;
import com.myapp.model.Cart;
import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    // Hàm này giúp lấy danh sách sản phẩm trong giỏ hàng
    List<CartItemDTO> getCartItems(); // Trả về danh sách sản phẩm trong giỏ hàng
    // Hàm này giúp thêm sản phẩm vào giỏ hàng
    CartItemDTO addToCart(CartItemDTO cartItemDTO); // Nhập thông tin sản phẩm, trả về sản phẩm đã thêm
    // Hàm này giúp cập nhật sản phẩm trong giỏ hàng
    CartItemDTO updateCartItem(CartItemDTO cartItemDTO); // Nhập thông tin mới, trả về sản phẩm đã cập nhật
    // Hàm này giúp xóa sản phẩm khỏi giỏ hàng
    void removeFromCart(Integer id); // Nhập mã số, xóa sản phẩm đó khỏi giỏ hàng
    // Hàm này giúp xóa toàn bộ sản phẩm trong giỏ hàng
    void clearCart(); // Xóa hết sản phẩm trong giỏ hàng
    // Hàm này giúp tính tổng số tiền trong giỏ hàng
    BigDecimal getCartTotal(); // Trả về tổng số tiền
    // Hàm này giúp lấy giỏ hàng của một người dùng
    List<Cart> getCartByUser(UserDTO userDTO); // Nhập thông tin người dùng, trả về giỏ hàng của người đó
    // Hàm này giúp thêm sản phẩm vào giỏ hàng của một người dùng
    void addToCart(UserDTO userDTO, ProductDTO productDTO, Integer quantity); // Nhập người dùng, sản phẩm, số lượng
    // Hàm này giúp cập nhật số lượng sản phẩm trong giỏ hàng
    void updateCartQuantity(Integer id, Integer quantity); // Nhập mã số sản phẩm và số lượng mới
    // Hàm này giúp tính tổng số tiền của một danh sách sản phẩm trong giỏ hàng
    BigDecimal calculateTotal(List<Cart> cartItems); // Nhập danh sách sản phẩm, trả về tổng số tiền
} 