// Khai báo package cho controller xử lý kết quả thanh toán của người dùng
package com.myapp.controller.user;

/*// Dòng này để đánh dấu đây là một controller (nơi xử lý các yêu cầu từ người dùng)
import org.springframework.stereotype.Controller;
// Dòng này để đánh dấu các hàm xử lý yêu cầu từ web
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

// Đoạn này nói rằng file này dùng để xử lý các trang kết quả thanh toán
@Controller
@RequestMapping("/user/checkout/payment")
public class PaymentResultController {
    // Hiển thị kết quả thanh toán VNPay
    public String vnpayReturn(@RequestParam(required = false) String vnp_ResponseCode, Model model) {
        if ("00".equals(vnp_ResponseCode)) {
            model.addAttribute("success", true); // Thanh toán thành công
        } else {
            model.addAttribute("success", false); // Thanh toán thất bại
        }
        return "user/payment-result"; // Trả về view kết quả thanh toán
    }
}
// Kết thúc file, các hàm trên giúp chuyển hướng người dùng đến trang thông báo kết quả thanh toán 

*/
