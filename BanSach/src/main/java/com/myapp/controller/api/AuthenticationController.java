package com.myapp.controller.api;

import com.myapp.dto.UserDTO;
import com.myapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller xử lý các request API liên quan đến xác thực người dùng
 * 
 * Sử dụng annotation @RestController để đánh dấu đây là một REST API Controller
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 * @RequestMapping("/api/auth"): Tất cả các request trong controller này đều bắt đầu bằng "/api/auth"
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    /**
     * Service xử lý logic liên quan đến người dùng
     * Được inject thông qua constructor
     */
    private final UserService userService;

    /**
     * Xử lý request POST để đăng ký tài khoản mới
     * 
     * @param userDTO Dữ liệu người dùng từ request body
     * @return ResponseEntity chứa thông tin người dùng đã đăng ký
     * 
     * URL mapping: "/api/auth/register"
     * Chức năng:
     * - Xử lý dữ liệu đăng ký từ request
     * - Gọi service để thực hiện đăng ký
     * - Trả về thông tin người dùng đã đăng ký
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.register(userDTO));
    }

    /**
     * Xử lý request POST để đăng nhập
     * 
     * @param userDTO Dữ liệu đăng nhập từ request body
     * @return ResponseEntity chứa thông tin người dùng đã đăng nhập
     * 
     * URL mapping: "/api/auth/login"
     * Chức năng:
     * - Xử lý dữ liệu đăng nhập từ request
     * - Gọi service để thực hiện đăng nhập
     * - Trả về thông tin người dùng đã đăng nhập
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.login(userDTO.getEmail(), userDTO.getMatKhau()));
    }

    /**
     * Xử lý request GET để lấy thông tin người dùng theo ID
     * 
     * @param id ID của người dùng
     * @return ResponseEntity chứa thông tin người dùng
     * 
     * URL mapping: "/api/auth/{id}"
     * Chức năng:
     * - Lấy thông tin người dùng theo ID
     * - Trả về thông tin người dùng
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * Xử lý request PUT để cập nhật thông tin người dùng
     * 
     * @param id ID của người dùng
     * @param userDTO Dữ liệu cập nhật từ request body
     * @return ResponseEntity chứa thông tin người dùng đã cập nhật
     * 
     * URL mapping: "/api/auth/{id}"
     * Chức năng:
     * - Cập nhật thông tin người dùng theo ID
     * - Trả về thông tin người dùng đã cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(id, userDTO));
    }

    /**
     * Xử lý request DELETE để xóa người dùng
     * 
     * @param id ID của người dùng cần xóa
     * @return ResponseEntity trống với status 200
     * 
     * URL mapping: "/api/auth/{id}"
     * Chức năng:
     * - Xóa người dùng theo ID
     * - Trả về response trống với status 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
} 