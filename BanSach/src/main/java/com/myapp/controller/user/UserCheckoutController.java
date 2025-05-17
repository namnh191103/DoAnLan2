// Khai báo package cho controller xử lý thanh toán đơn hàng
package com.myapp.controller.user;

// Import các thư viện cần thiết
import com.myapp.dto.UserDTO;
import com.myapp.service.CartService;
import com.myapp.service.DonHangUserService;
import com.myapp.service.ShippingMethodService;
import com.myapp.service.PaymentMethodService;
import com.myapp.service.UserService;
import com.myapp.service.VNPayService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import com.myapp.model.TrangThaiDonHang;

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
    private final DonHangUserService donHangUserService;

    /**
     * Service xử lý logic liên quan đến người dùng
     * Được inject thông qua constructor
     */
    private final UserService userService;

    /**
     * Service xử lý logic liên quan đến phương thức vận chuyển
     * Được inject thông qua constructor
     */
    private final ShippingMethodService shippingMethodService;

    /**
     * Service xử lý logic liên quan đến phương thức thanh toán
     * Được inject thông qua constructor
     */
    private final PaymentMethodService paymentMethodService;

    /**
     * Service xử lý logic liên quan đến VNPay
     * Được inject thông qua constructor
     */
    private final VNPayService vnpayService;

    private static final Logger log = LoggerFactory.getLogger(UserCheckoutController.class);

    /**
     * Xử lý request GET để hiển thị trang thanh toán
     * 
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @param model Model để truyền dữ liệu đến view
     * @param selectedShippingMethodId ID của phương thức vận chuyển được chọn
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
    public String checkoutPage(Authentication authentication, Model model, @RequestParam(value = "shippingMethodId", required = false) Integer selectedShippingMethodId) {
        String email = authentication.getName();
        UserDTO userDTO = userService.findByEmail(email);
        var cartItems = cartService.getCartByUser(userDTO);
        var shippingMethods = shippingMethodService.findAllActive();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.calculateTotal(cartItems));
        model.addAttribute("user", userDTO);
        model.addAttribute("shippingMethods", shippingMethods);
        model.addAttribute("paymentMethods", paymentMethodService.findAllActive());
        if (selectedShippingMethodId == null && !shippingMethods.isEmpty()) {
            selectedShippingMethodId = shippingMethods.get(0).getId();
        }
        model.addAttribute("selectedShippingMethodId", selectedShippingMethodId);
        model.addAttribute("selectedShippingMethod", selectedShippingMethodId != null ? shippingMethodService.findById(selectedShippingMethodId) : null);
        return "user/checkout";
    }

    /**
     * Xử lý request POST để tạo đơn hàng mới
     * 
     * @param authentication Thông tin xác thực của người dùng hiện tại
     * @param shippingAddress Địa chỉ giao hàng
     * @param phoneNumber Số điện thoại người nhận
     * @param paymentMethod Phương thức thanh toán
     * @param shippingMethodId ID của phương thức vận chuyển
     * @param note Ghi chú đơn hàng
     * @param request HttpServletRequest
     * @param model Model để truyền dữ liệu đến view
     * @return 
     * - Nếu thành công: Chuyển hướng đến trang chi tiết đơn hàng
     * - Nếu thất bại: Chuyển hướng đến trang thanh toán với thông báo lỗi
     * 
     * URL mapping: "/user/checkout"
     * Chức năng:
     * - Lấy thông tin người dùng đang đăng nhập
     * - Tạo đơn hàng mới với thông tin từ form
     * - Xử lý các trường hợp thành công/thất bại
     */
    @PostMapping
    public String placeOrder(
            Authentication authentication,
            @RequestParam String shippingAddress,
            @RequestParam String phoneNumber,
            @RequestParam String paymentMethod,
            @RequestParam Integer shippingMethodId,
            @RequestParam(required = false) String note,
            HttpServletRequest request,
            Model model) {
        String email = authentication.getName();
        UserDTO userDTO = userService.findByEmail(email);
        var shippingMethod = shippingMethodService.findById(shippingMethodId);
        var paymentMethodObj = paymentMethodService.findAllActive().stream().findFirst().orElse(null); // Lấy paymentMethod đầu tiên (mock)
        try {
            if ("COD".equals(paymentMethod)) {
                var donHang = donHangUserService.createOrder(userDTO, shippingAddress, phoneNumber, note, shippingMethod, paymentMethodObj);
                log.info("Đặt hàng COD thành công, đơn hàng id={}", donHang.getId());
                return "redirect:/user/payment-success";
            } else if ("VNPAY".equals(paymentMethod)) {
                var donHang = donHangUserService.createOrderWithStatus(userDTO, shippingAddress, phoneNumber, note, shippingMethod, paymentMethodObj, TrangThaiDonHang.CHO_THANH_TOAN);
                String paymentUrl = vnpayService.createPaymentUrl(donHang.getId(), donHang.getTongThanhToan(), request);
                log.info("Đặt hàng VNPay, redirect đến VNPay, đơn hàng id={}", donHang.getId());
                return "redirect:" + paymentUrl;
            } else {
                model.addAttribute("error", "Phương thức thanh toán không hợp lệ");
                return "user/checkout";
            }
        } catch (Exception e) {
            log.error("Lỗi khi đặt hàng: {}", e.getMessage(), e);
            model.addAttribute("error", "Có lỗi xảy ra khi đặt hàng: " + e.getMessage());
            return "user/checkout";
        }
    }

    @GetMapping("/payment/vnpay-return")
    public String vnpayReturn(@RequestParam Map<String, String> params, Model model) {
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        String vnp_TxnRef = params.get("vnp_TxnRef");
        String vnp_SecureHash = params.get("vnp_SecureHash");
        if (!vnpayService.validateSignature(params, vnp_SecureHash)) {
            log.warn("VNPay callback: Sai chữ ký! TxnRef={}", vnp_TxnRef);
            model.addAttribute("error", "Sai chữ ký VNPay!");
            return "user/payment-fail";
        }
        var donHang = donHangUserService.getOrderById(Integer.valueOf(vnp_TxnRef));
        if (donHang == null) {
            log.error("VNPay callback: Không tìm thấy đơn hàng id={}", vnp_TxnRef);
            model.addAttribute("error", "Không tìm thấy đơn hàng!");
            return "user/payment-fail";
        }
        if (!donHang.getTrangThai().equals(TrangThaiDonHang.CHO_THANH_TOAN)) {
            log.warn("VNPay callback: Đơn hàng không ở trạng thái chờ thanh toán, id={}", donHang.getId());
            model.addAttribute("error", "Trạng thái đơn hàng không hợp lệ!");
            return "user/payment-fail";
        }
        if ("00".equals(vnp_ResponseCode)) {
            donHang.setTrangThai(TrangThaiDonHang.DA_THANH_TOAN);
            donHangUserService.save(donHang);
            log.info("Thanh toán VNPay thành công, đơn hàng id={}", donHang.getId());
            // Xóa giỏ hàng nếu cần
            model.addAttribute("success", "Thanh toán thành công!");
            return "user/payment-success";
        } else {
            donHang.setTrangThai(TrangThaiDonHang.THANH_TOAN_THAT_BAI);
            donHangUserService.save(donHang);
            log.warn("Thanh toán VNPay thất bại, đơn hàng id={}", donHang.getId());
            model.addAttribute("error", "Thanh toán thất bại!");
            return "user/payment-fail";
        }
    }
} 