// Khai báo package cho controller dashboard admin
package com.myapp.controller.admin;

// Import các thư viện và class cần thiết
import com.myapp.service.DonHangService; // Service xử lý logic liên quan đến đơn hàng
import lombok.RequiredArgsConstructor; // Annotation tự động tạo constructor với các biến final
import org.springframework.stereotype.Controller; // Đánh dấu đây là một controller
import org.springframework.ui.Model; // Đối tượng truyền dữ liệu sang view
import org.springframework.web.bind.annotation.GetMapping; // Annotation cho phương thức GET
import com.fasterxml.jackson.databind.ObjectMapper; // Thư viện chuyển đổi đối tượng sang JSON

@RequiredArgsConstructor // Tự động tạo constructor với các biến final
@Controller // Đánh dấu đây là một controller
public class DashboardController {
    private final DonHangService donHangService; // Service xử lý logic đơn hàng
    @GetMapping("/admin/dashboard") // Xử lý request GET cho dashboard admin
    public String dashboard(Model model) {
        var stats = donHangService.getDashboardStats(); // Lấy thống kê dashboard
        System.out.println("[DASHBOARD] tongDoanhThu: " + stats.getTongDoanhThu()); // In ra tổng doanh thu
        System.out.println("[DASHBOARD] tongSoDon: " + stats.getTongSoDon()); // In ra tổng số đơn
        System.out.println("[DASHBOARD] soDonHoanThanh: " + stats.getSoDonHoanThanh()); // In ra số đơn hoàn thành
        System.out.println("[DASHBOARD] doanhThu7Ngay: " + stats.getDoanhThu7Ngay()); // In ra doanh thu 7 ngày gần nhất
        model.addAttribute("tongDoanhThu", stats.getTongDoanhThu()); // Truyền tổng doanh thu sang view
        model.addAttribute("tongSoDon", stats.getTongSoDon()); // Truyền tổng số đơn sang view
        model.addAttribute("soDonHoanThanh", stats.getSoDonHoanThanh()); // Truyền số đơn hoàn thành sang view
        try {
            model.addAttribute("doanhThu7NgayJson", new ObjectMapper().writeValueAsString(stats.getDoanhThu7Ngay())); // Chuyển doanh thu 7 ngày sang JSON để hiển thị biểu đồ
        } catch (Exception e) {
            model.addAttribute("doanhThu7NgayJson", "[]"); // Nếu lỗi thì truyền mảng rỗng
        }
        return "admin/dashboard"; // Trả về view dashboard
    }
} 