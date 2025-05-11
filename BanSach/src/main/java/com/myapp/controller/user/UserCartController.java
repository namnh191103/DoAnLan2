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

/**
 * Controller xử lý các request liên quan đến giỏ hàng của người dùng
 * 
 * Sử dụng annotation @Controller để đánh dấu đây là một Spring MVC Controller
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 * @RequestMapping("/user/cart"): Tất cả các request trong controller này đều bắt đầu bằng "/user/cart"
 */
@Controller
@RequestMapping("/user/cart")
@RequiredArgsConstructor
public class UserCartController {
    /**
     * Service xử lý logic liên quan đến giỏ hàng
     * Được inject thông qua constructor
     */
    private final CartService cartService;

    /**
     * Service xử lý logic liên quan đến người dùng
     * Được inject thông qua constructor
     */
    private final UserService userService;

    /**
     * Service xử lý logic liên quan đến sản phẩm
     * Được inject thông qua constructor
     */
    private final ProductService productService;

    /**
     * Xử lý request GET cho trang giỏ hàng
     * 
     * @param model Model để truyền dữ liệu đến view
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @return Tên view template "user/cart"
     * 
     * URL mapping: "/user/cart"
     * Chức năng:
     * - Lấy thông tin người dùng đang đăng nhập
     * - Lấy danh sách sản phẩm trong giỏ hàng
     * - Tính tổng tiền giỏ hàng
     * - Thêm thông tin vào model để hiển thị trên view
     */
    @GetMapping
    public String viewCart(Model model, Authentication authentication) {
        String email = authentication.getName();
        UserDTO userDTO = userService.findByEmail(email);
        List<Cart> cartItems = cartService.getCartByUser(userDTO);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.calculateTotal(cartItems));
        return "user/cart";
    }

    /**
     * Xử lý request POST để thêm sản phẩm vào giỏ hàng
     * 
     * @param productId ID của sản phẩm cần thêm
     * @param quantity Số lượng sản phẩm
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @return 
     * - Nếu thành công: "success"
     * - Nếu thất bại: "error: [thông báo lỗi]"
     * 
     * URL mapping: "/user/cart/add"
     * Chức năng:
     * - Lấy thông tin người dùng đang đăng nhập
     * - Lấy thông tin sản phẩm từ database
     * - Thêm sản phẩm vào giỏ hàng với số lượng chỉ định
     */
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

    /**
     * Xử lý request POST để xóa sản phẩm khỏi giỏ hàng
     * 
     * @param id ID của item trong giỏ hàng cần xóa
     * @return 
     * - Nếu thành công: "success"
     * - Nếu thất bại: "error: [thông báo lỗi]"
     * 
     * URL mapping: "/user/cart/remove/{id}"
     * Chức năng: Xóa một sản phẩm khỏi giỏ hàng theo ID
     */
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

    /**
     * Xử lý request POST để cập nhật số lượng sản phẩm trong giỏ hàng
     * 
     * @param id ID của item trong giỏ hàng cần cập nhật
     * @param quantity Số lượng mới
     * @return 
     * - Nếu thành công: "redirect:/user/cart"
     * - Nếu thất bại: "redirect:/user/cart?error=[thông báo lỗi]"
     * 
     * URL mapping: "/user/cart/update/{id}"
     * Chức năng: Cập nhật số lượng của một sản phẩm trong giỏ hàng
     */
    @PostMapping("/update/{id}")
    public String updateCartQuantity(@PathVariable Integer id,
                                   @RequestParam Integer quantity) {
        try {
            cartService.updateCartQuantity(id, quantity);
            return "redirect:/user/cart";
        } catch (Exception e) {
            return "redirect:/user/cart?error=" + e.getMessage();
        }
    }
} 