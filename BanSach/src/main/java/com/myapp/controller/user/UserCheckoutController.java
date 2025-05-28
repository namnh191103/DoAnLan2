// Dòng này giống như ghi địa chỉ để máy tính biết file này nằm ở đâu
// Khai báo package cho controller xử lý thanh toán đơn hàng của người dùng
package com.myapp.controller.user;

// Dòng này để lấy thông tin về người dùng dưới dạng dữ liệu trung gian
import com.myapp.dto.UserDTO;
// Dòng này để sử dụng các chức năng liên quan đến giỏ hàng
import com.myapp.service.CartService;
// Dòng này để sử dụng các chức năng liên quan đến đơn hàng
import com.myapp.service.DonHangUserService;
// Dòng này để sử dụng các chức năng liên quan đến phương thức giao hàng
import com.myapp.service.ShippingMethodService;
// Dòng này để sử dụng các chức năng liên quan đến phương thức thanh toán
import com.myapp.service.PaymentMethodService;
// Dòng này để sử dụng các chức năng liên quan đến người dùng
import com.myapp.service.UserService;
// Dòng này để sử dụng các chức năng liên quan đến thanh toán VNPay
import com.myapp.service.VNPayService;
// Dòng này giúp tự động tạo hàm khởi tạo khi có các biến final
import lombok.RequiredArgsConstructor;
// Dòng này để ghi log (ghi lại các sự kiện quan trọng khi chạy chương trình)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Dòng này để lấy thông tin xác thực của người dùng đang đăng nhập
import org.springframework.security.core.Authentication;
// Dòng này để đánh dấu đây là một controller (nơi xử lý các yêu cầu từ người dùng)
import org.springframework.stereotype.Controller;
// Dòng này để truyền dữ liệu từ controller sang giao diện (view)
import org.springframework.ui.Model;
// Dòng này để đánh dấu các hàm xử lý yêu cầu từ web
import org.springframework.web.bind.annotation.*;
// Dòng này để lấy thông tin từ request của người dùng (ví dụ: địa chỉ IP, tham số truyền lên)
import jakarta.servlet.http.HttpServletRequest;
// Dòng này để sử dụng kiểu dữ liệu bản đồ (Map)
import java.util.Map;
// Dòng này để lấy thông tin về trạng thái đơn hàng
import com.myapp.model.TrangThaiDonHang;

/**
 * Controller xử lý các request liên quan đến thanh toán đơn hàng
 * @Controller: Dán nhãn cho biết đây là một controller
 * @RequiredArgsConstructor: Tự động tạo hàm khởi tạo với các biến final
 * @RequestMapping("/user/checkout"): Tất cả các đường dẫn trong file này đều bắt đầu bằng /user/checkout
 */
@Controller
@RequestMapping("/user/checkout")
@RequiredArgsConstructor
public class UserCheckoutController {
    // Đây là nơi xử lý các chức năng liên quan đến giỏ hàng
    private final CartService cartService;
    // Đây là nơi xử lý các chức năng liên quan đến đơn hàng
    private final DonHangUserService donHangUserService;
    // Đây là nơi xử lý các chức năng liên quan đến người dùng
    private final UserService userService;
    // Đây là nơi xử lý các chức năng liên quan đến phương thức giao hàng
    private final ShippingMethodService shippingMethodService;
    // Đây là nơi xử lý các chức năng liên quan đến phương thức thanh toán
    private final PaymentMethodService paymentMethodService;
    // Đây là nơi xử lý các chức năng liên quan đến thanh toán VNPay
    private final VNPayService vnpayService;
    // Dòng này để ghi log (ghi lại các sự kiện quan trọng khi chạy chương trình)
    private static final Logger log = LoggerFactory.getLogger(UserCheckoutController.class);

    // Hàm này xử lý khi người dùng vào trang thanh toán
    @GetMapping
    public String checkoutPage(Authentication authentication, Model model, @RequestParam(value = "shippingMethodId", required = false) Integer selectedShippingMethodId) {
        // Lấy email của người dùng đang đăng nhập
        String email = authentication.getName();
        // Lấy thông tin người dùng
        UserDTO userDTO = userService.findByEmail(email);
        // Lấy danh sách sản phẩm trong giỏ hàng
        var cartItems = cartService.getCartByUser(userDTO);
        // Lấy danh sách phương thức giao hàng đang hoạt động
        var shippingMethods = shippingMethodService.findAllActive();
        // Đưa các thông tin vào model để hiển thị lên giao diện
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.calculateTotal(cartItems));
        model.addAttribute("user", userDTO);
        model.addAttribute("shippingMethods", shippingMethods);
        model.addAttribute("paymentMethods", paymentMethodService.findAllActive());
        // Nếu chưa chọn phương thức giao hàng thì chọn mặc định là cái đầu tiên
        if (selectedShippingMethodId == null && !shippingMethods.isEmpty()) {
            selectedShippingMethodId = shippingMethods.get(0).getId();
        }
        model.addAttribute("selectedShippingMethodId", selectedShippingMethodId);
        model.addAttribute("selectedShippingMethod", selectedShippingMethodId != null ? shippingMethodService.findById(selectedShippingMethodId) : null);
        // Trả về tên giao diện (view) để hiển thị
        return "user/checkout";
    }

    // Hàm này xử lý khi người dùng bấm nút đặt hàng
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
        // Lấy email của người dùng đang đăng nhập
        String email = authentication.getName();
        // Lấy thông tin người dùng
        UserDTO userDTO = userService.findByEmail(email);
        // Lấy phương thức giao hàng được chọn
        var shippingMethod = shippingMethodService.findById(shippingMethodId);
        // Lấy phương thức thanh toán đầu tiên (chỗ này chỉ lấy tạm, thực tế nên lấy đúng theo người dùng chọn)
        var paymentMethodObj = paymentMethodService.findAllActive().stream().findFirst().orElse(null); // Chỗ này cần kiểm tra thêm, chưa rõ lắm
        try {
            // Nếu chọn thanh toán khi nhận hàng (COD)
            if ("COD".equals(paymentMethod)) {
                var donHang = donHangUserService.createOrder(userDTO, shippingAddress, phoneNumber, note, shippingMethod, paymentMethodObj);
                log.info("Đặt hàng COD thành công, đơn hàng id={}", donHang.getId());
                // Chuyển sang trang thông báo thành công
                return "redirect:/user/checkout/payment-success";
            } else if ("VNPAY".equals(paymentMethod)) {
                // Nếu chọn thanh toán qua VNPay
                var donHang = donHangUserService.createOrderWithStatus(userDTO, shippingAddress, phoneNumber, note, shippingMethod, paymentMethodObj, TrangThaiDonHang.CHO_THANH_TOAN);
                String paymentUrl = vnpayService.createPaymentUrl(donHang.getId(), donHang.getTongThanhToan(), request);
                log.info("Đặt hàng VNPay, redirect đến VNPay, đơn hàng id={}", donHang.getId());
                // Chuyển hướng sang trang thanh toán VNPay
                return "redirect:" + paymentUrl;
            } else {
                // Nếu chọn phương thức thanh toán không hợp lệ
                model.addAttribute("error", "Phương thức thanh toán không hợp lệ");
                return "user/checkout";
            }
        } catch (Exception e) {
            // Nếu có lỗi khi đặt hàng
            log.error("Lỗi khi đặt hàng: {}", e.getMessage(), e);
            model.addAttribute("error", "Có lỗi xảy ra khi đặt hàng: " + e.getMessage());
            return "user/checkout";
        }
    }

    // Hàm này xử lý khi VNPay trả kết quả về cho hệ thống
    @GetMapping("/payment/vnpay-return")
    public String vnpayReturn(@RequestParam Map<String, String> params, Model model) {
        // Lấy các thông tin trả về từ VNPay
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        String vnp_TxnRef = params.get("vnp_TxnRef");
        String vnp_SecureHash = params.get("vnp_SecureHash");
        // Kiểm tra tính hợp lệ của dữ liệu trả về
        if (!vnpayService.validateSignature(params, vnp_SecureHash)) {
            log.warn("VNPay callback: Sai chữ ký! TxnRef={}", vnp_TxnRef);
            model.addAttribute("error", "Sai chữ ký VNPay!");
            return "user/payment-fail";
        }
        // Tìm đơn hàng dựa vào mã số trả về
        var donHang = donHangUserService.getOrderById(Integer.valueOf(vnp_TxnRef));
        if (donHang == null) {
            log.error("VNPay callback: Không tìm thấy đơn hàng id={}", vnp_TxnRef);
            model.addAttribute("error", "Không tìm thấy đơn hàng!");
            return "user/payment-fail";
        }
        // Kiểm tra trạng thái đơn hàng
        if (!donHang.getTrangThai().equals(TrangThaiDonHang.CHO_THANH_TOAN)) {
            log.warn("VNPay callback: Đơn hàng không ở trạng thái chờ thanh toán, id={}", donHang.getId());
            model.addAttribute("error", "Trạng thái đơn hàng không hợp lệ!");
            return "user/payment-fail";
        }
        // Nếu thanh toán thành công
        if ("00".equals(vnp_ResponseCode)) {
            donHang.setTrangThai(TrangThaiDonHang.DA_THANH_TOAN);
            donHangUserService.save(donHang);
            log.info("Thanh toán VNPay thành công, đơn hàng id={}", donHang.getId());
            // Thêm thông báo thành công vào model
            model.addAttribute("success", "Thanh toán thành công!");
            return "user/payment-success";
        } else {
            // Nếu thanh toán thất bại
            donHang.setTrangThai(TrangThaiDonHang.THANH_TOAN_THAT_BAI);
            donHangUserService.save(donHang);
            log.warn("Thanh toán VNPay thất bại, đơn hàng id={}", donHang.getId());
            model.addAttribute("error", "Thanh toán thất bại!");
            return "user/payment-fail";
        }
    }

    // Mapping hiển thị trang thanh toán thành công
    @GetMapping("/payment-success")
    public String paymentSuccess() {
        return "user/payment-success";
    }

    // Mapping hiển thị trang thanh toán thất bại
    @GetMapping("/payment-fail")
    public String paymentFail() {
        return "user/payment-fail";
    }
}
// Kết thúc file, các hàm trên giúp người dùng đặt hàng, chọn phương thức thanh toán, xử lý kết quả thanh toán một cách dễ hiểu 