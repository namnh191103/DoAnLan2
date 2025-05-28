package com.myapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "shipping_method")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Khóa chính, tự động tăng

    @Column(nullable = false, unique = true)
    private String name; // Tên phương thức giao hàng, không được trùng lặp

    @Column(length = 255)
    private String description; // Mô tả chi tiết phương thức giao hàng

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fee; // Phí giao hàng, nên kiểm tra không âm ở tầng service

    @Column(nullable = false)
    private boolean active; // Đánh dấu phương thức này có đang hoạt động không
    // Nếu thêm trường mới, cần kiểm tra các nơi mapping DTO <-> Entity
} 
// Cảnh báo: Nếu thay đổi logic tính phí, cần kiểm tra các nơi sử dụng trường fee 