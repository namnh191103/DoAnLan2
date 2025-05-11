// Khai báo package cho controller xử lý thanh toán đơn hàng
package com.myapp.controller.user;

// Import các thư viện cần thiết
import com.myapp.dto.UserDTO;
import com.myapp.service.CartService;
import com.myapp.service.OrderService;
import com.myapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller xử lý các request liên quan đến thanh toán đơn hàng
 * 
 * Sử dụng annotation @Controller để đánh dấu đây là một Spring MVC Controller
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 * @RequestMapping("/user/checkout"): Tất cả các request trong controller này đều bắt đầu bằng "/user/checkout"
 */
@Controller
@RequestMapping("/user/checkout")
@RequiredArgsConstructor
public class UserCheckoutController {
    /**
     * Service xử lý logic liên quan đến giỏ hàng
     * Được inject thông qua constructor
     */
    private final CartService cartService;

    /**
     * Service xử lý logic liên quan đến đơn hàng
     * Được inject thông qua constructor
     */
    private final OrderService orderService;

    /**
     * Service xử lý logic liên quan đến người dùng
     * Được inject thông qua constructor
     */
    private final UserService userService;

    /**
     * Xử lý request GET để hiển thị trang thanh toán
     * 
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @param model Model để truyền dữ liệu đến view
     * @return Tên view template "user/checkout"
     * 
     * URL mapping: "/user/checkout"
     * Chức năng:
     * - Lấy thông tin người dùng đang đăng nhập
     * - Lấy danh sách sản phẩm trong giỏ hàng
     * - Tính tổng tiền giỏ hàng
     * - Thêm thông tin vào model để hiển thị trên view
     */
    @GetMapping
    public String checkoutPage(Authentication authentication, Model model) {
        String email = authentication.getName();
        UserDTO userDTO = userService.findByEmail(email);
        var cartItems = cartService.getCartByUser(userDTO);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.calculateTotal(cartItems));
        model.addAttribute("user", userDTO);
        return "user/checkout";
    }

    /**
     * Xử lý request POST để tạo đơn hàng mới
     * 
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @param shippingAddress Địa chỉ giao hàng
     * @param phoneNumber Số điện thoại người nhận
     * @param note Ghi chú đơn hàng
     * @return 
     * - Nếu thành công: Chuyển hướng đến trang chi tiết đơn hàng
     * - Nếu thất bại: Chuyển hướng đến trang thanh toán với thông báo lỗi
     * 
     * URL mapping: "/user/checkout/place-order"
     * Chức năng:
     * - Lấy thông tin người dùng đang đăng nhập
     * - Tạo đơn hàng mới với thông tin từ form
     * - Xử lý các trường hợp thành công/thất bại
     */
    @PostMapping("/place-order")
    public String placeOrder(
            Authentication authentication,
            @RequestParam String shippingAddress,
            @RequestParam String phoneNumber,
            @RequestParam(required = false) String note) {
        try {
            String email = authentication.getName();
            UserDTO userDTO = userService.findByEmail(email);
            var order = orderService.createOrder(userDTO, shippingAddress, phoneNumber, note);
            return "redirect:/user/orders/" + order.getId() + "?success=Ordered";
        } catch (Exception e) {
            return "redirect:/user/checkout?error=" + e.getMessage();
        }
    }
} 