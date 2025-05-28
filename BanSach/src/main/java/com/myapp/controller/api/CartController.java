// Khai báo package cho controller API quản lý giỏ hàng
package com.myapp.controller.api;

// Import các thư viện và class cần thiết
import com.myapp.dto.CartItemDTO; // Đối tượng truyền dữ liệu item giỏ hàng
import com.myapp.service.CartService; // Service xử lý logic liên quan đến giỏ hàng
import lombok.RequiredArgsConstructor; // Annotation tự động tạo constructor với các biến final
import org.springframework.http.ResponseEntity; // Đối tượng trả về phản hồi HTTP
import org.springframework.web.bind.annotation.*; // Import các annotation cho REST controller
import java.math.BigDecimal; // Kiểu số lớn cho tổng tiền
import java.util.List; // Thư viện danh sách

@RestController // Đánh dấu đây là REST API Controller
@RequestMapping("/api/cart") // Đường dẫn gốc cho các API giỏ hàng
@RequiredArgsConstructor // Tự động tạo constructor với các biến final
public class CartController {
    private final CartService cartService; // Service xử lý logic giỏ hàng

    // Lấy danh sách item trong giỏ hàng
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCartItems() {
        return ResponseEntity.ok(cartService.getCartItems());
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/items")
    public ResponseEntity<CartItemDTO> addToCart(@RequestBody CartItemDTO cartItemDTO) {
        return ResponseEntity.ok(cartService.addToCart(cartItemDTO));
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @PutMapping("/items/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @PathVariable Integer id,
            @RequestBody CartItemDTO cartItemDTO) {
        cartItemDTO.setId(id);
        return ResponseEntity.ok(cartService.updateCartItem(cartItemDTO));
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Integer id) {
        cartService.removeFromCart(id);
        return ResponseEntity.ok().build();
    }

    // Xóa toàn bộ giỏ hàng
    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok().build();
    }

    // Lấy tổng tiền giỏ hàng
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getCartTotal() {
        return ResponseEntity.ok(cartService.getCartTotal());
    }
} 