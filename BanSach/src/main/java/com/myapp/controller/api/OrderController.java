// Khai báo package cho controller API quản lý đơn hàng
package com.myapp.controller.api;

// Import các thư viện và class cần thiết
import com.myapp.dto.DonHangDTO; // Đối tượng truyền dữ liệu đơn hàng
import com.myapp.service.DonHangService; // Service xử lý logic liên quan đến đơn hàng
import lombok.RequiredArgsConstructor; // Annotation tự động tạo constructor với các biến final
import org.springframework.data.domain.Page; // Đối tượng phân trang
import org.springframework.data.domain.PageRequest; // Đối tượng tạo yêu cầu phân trang
import org.springframework.http.ResponseEntity; // Đối tượng trả về phản hồi HTTP
import org.springframework.web.bind.annotation.*; // Import các annotation cho REST controller
import java.util.List; // Thư viện danh sách

@RestController // Đánh dấu đây là REST API Controller
@RequestMapping("/api/orders") // Đường dẫn gốc cho các API đơn hàng
@RequiredArgsConstructor // Tự động tạo constructor với các biến final
public class OrderController {
    private final DonHangService donHangService; // Service xử lý logic đơn hàng

    // Lấy danh sách đơn hàng có phân trang
    @GetMapping
    public ResponseEntity<Page<DonHangDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(donHangService.findAll(PageRequest.of(page, size)));
    }

    // Lấy đơn hàng theo id
    @GetMapping("/{id}")
    public ResponseEntity<DonHangDTO> getOrderById(@PathVariable Integer id) {
        DonHangDTO order = donHangService.findById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }

    // Tạo mới đơn hàng
    @PostMapping
    public ResponseEntity<DonHangDTO> createOrder(@RequestBody DonHangDTO dto) {
        return ResponseEntity.ok(donHangService.create(dto));
    }

    // Cập nhật đơn hàng
    @PutMapping("/{id}")
    public ResponseEntity<DonHangDTO> updateOrder(@PathVariable Integer id, @RequestBody DonHangDTO dto) {
        DonHangDTO updated = donHangService.update(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa đơn hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        donHangService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Lấy danh sách đơn hàng theo user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DonHangDTO>> getOrdersByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(donHangService.findByUserId(userId));
    }

    // Lấy danh sách đơn hàng theo trạng thái
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<DonHangDTO>> getOrdersByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(donHangService.findByStatus(status, PageRequest.of(page, size)));
    }
} 