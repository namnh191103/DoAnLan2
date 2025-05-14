// Khai báo package cho controller xử lý đơn hàng của người dùng
package com.myapp.controller.user;

// Import các thư viện cần thiết
import com.myapp.dto.UserDTO;
import com.myapp.service.DonHangUserService;
import com.myapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller xử lý các request liên quan đến đơn hàng của người dùng
 * 
 * Sử dụng annotation @Controller để đánh dấu đây là một Spring MVC Controller
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 * @RequestMapping("/user/orders"): Tất cả các request trong controller này đều bắt đầu bằng "/user/orders"
 */
@Controller
@RequestMapping("/user/orders")
@RequiredArgsConstructor
public class UserOrderController {
    /**
     * Service xử lý logic liên quan đến đơn hàng
     * Được inject thông qua constructor
     */
    private final DonHangUserService donHangUserService;

    /**
     * Service xử lý logic liên quan đến người dùng
     * Được inject thông qua constructor
     */
    private final UserService userService;

    /**
     * Xử lý request GET để hiển thị danh sách đơn hàng của người dùng
     * 
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @param model Model để truyền dữ liệu đến view
     * @return Tên view template "user/orders/list"
     * 
     * URL mapping: "/user/orders"
     * Chức năng:
     * - Lấy thông tin người dùng đang đăng nhập
     * - Lấy danh sách đơn hàng của người dùng
     * - Thêm thông tin vào model để hiển thị trên view
     */
    @GetMapping
    public String listOrders(Authentication authentication, Model model) {
        String email = authentication.getName();
        UserDTO userDTO = userService.findByEmail(email);
        model.addAttribute("donHangs", donHangUserService.getOrdersByUser(userDTO));
        return "user/orders/list";
    }

    /**
     * Xử lý request GET để hiển thị chi tiết đơn hàng
     * 
     * @param id ID của đơn hàng
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @param model Model để truyền dữ liệu đến view
     * @return 
     * - Nếu tìm thấy đơn hàng: Tên view template "user/orders/detail"
     * - Nếu không tìm thấy: Chuyển hướng đến trang danh sách đơn hàng
     * 
     * URL mapping: "/user/orders/{id}"
     * Chức năng:
     * - Lấy thông tin người dùng đang đăng nhập
     * - Kiểm tra quyền truy cập đơn hàng
     * - Lấy chi tiết đơn hàng
     * - Thêm thông tin vào model để hiển thị trên view
     */
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Integer id,
                            Authentication authentication,
                            Model model) {
        String email = authentication.getName();
        UserDTO userDTO = userService.findByEmail(email);
        var donHang = donHangUserService.getOrderById(id);
        
        if (donHang == null || !donHang.getUser().getEmail().equals(email)) {
            return "redirect:/user/orders";
        }
        
        model.addAttribute("donHang", donHang);
        return "user/orders/detail";
    }

    /**
     * Xử lý request POST để hủy đơn hàng
     * 
     * @param id ID của đơn hàng cần hủy
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @return 
     * - Nếu thành công: Chuyển hướng đến trang chi tiết đơn hàng với thông báo thành công
     * - Nếu thất bại: Chuyển hướng đến trang chi tiết đơn hàng với thông báo lỗi
     * 
     * URL mapping: "/user/orders/{id}/cancel"
     * Chức năng:
     * - Lấy thông tin người dùng đang đăng nhập
     * - Kiểm tra quyền hủy đơn hàng
     * - Thực hiện hủy đơn hàng
     * - Xử lý các trường hợp thành công/thất bại
     */
    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Integer id,
                            Authentication authentication) {
        String email = authentication.getName();
        UserDTO userDTO = userService.findByEmail(email);
        try {
            donHangUserService.cancelOrder(id, userDTO);
            return "redirect:/user/orders/" + id + "?success=Canceled";
        } catch (Exception e) {
            return "redirect:/user/orders/" + id + "?error=" + e.getMessage();
        }
    }
} 