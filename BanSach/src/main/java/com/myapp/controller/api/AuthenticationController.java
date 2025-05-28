// Khai báo package cho controller API xác thực người dùng
package com.myapp.controller.api;

// Import các thư viện và class cần thiết
import com.myapp.dto.UserDTO; // Đối tượng truyền dữ liệu người dùng
import com.myapp.service.UserService; // Service xử lý logic liên quan đến người dùng
import lombok.RequiredArgsConstructor; // Annotation tự động tạo constructor với các biến final
import org.springframework.http.ResponseEntity; // Đối tượng trả về phản hồi HTTP
import org.springframework.web.bind.annotation.*; // Import các annotation cho REST controller

/**
 * Controller xử lý các request API liên quan đến xác thực người dùng
 * Sử dụng annotation @RestController để đánh dấu đây là một REST API Controller
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 * @RequestMapping("/api/auth"): Tất cả các request trong controller này đều bắt đầu bằng "/api/auth"
 */
@RestController // Đánh dấu đây là REST API Controller
@RequestMapping("/api/auth") // Đường dẫn gốc cho các API xác thực
@RequiredArgsConstructor // Tự động tạo constructor với các biến final
public class AuthenticationController {
    // Service xử lý logic liên quan đến người dùng, được inject qua constructor
    private final UserService userService;

    // Xử lý request POST để đăng ký tài khoản mới
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.register(userDTO)); // Gọi service đăng ký và trả về kết quả
    }

    // Xử lý request POST để đăng nhập
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.login(userDTO.getEmail(), userDTO.getMatKhau())); // Gọi service đăng nhập và trả về kết quả
    }

    // Xử lý request GET để lấy thông tin người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id)); // Lấy user theo id và trả về
    }

    // Xử lý request PUT để cập nhật thông tin người dùng
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(id, userDTO)); // Cập nhật user và trả về kết quả
    }

    // Xử lý request DELETE để xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.delete(id); // Xóa user theo id
        return ResponseEntity.ok().build(); // Trả về phản hồi rỗng (thành công)
    }
} 