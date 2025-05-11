package com.myapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity đại diện cho đơn hàng trong hệ thống
 * 
 * Sử dụng annotation @Entity để đánh dấu đây là một JPA Entity
 * @Table: Chỉ định tên bảng trong database
 * @Data: Tự động tạo getter, setter, toString, equals, hashCode
 */
@Entity
@Table(name = "orders")
@Data
public class Order {
    /**
     * ID của đơn hàng
     * @Id: Đánh dấu đây là khóa chính
     * @GeneratedValue: Tự động tăng giá trị
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Người đặt hàng
     * @ManyToOne: Quan hệ nhiều-1 với User
     * @JoinColumn: Chỉ định tên cột khóa ngoại
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Danh sách chi tiết đơn hàng
     * @OneToMany: Quan hệ 1-nhiều với OrderDetail
     * mappedBy: Chỉ định trường trong OrderDetail liên kết với Order
     * cascade: Tự động lưu/xóa OrderDetail khi Order được lưu/xóa
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    /**
     * Tổng tiền đơn hàng
     */
    private Double totalAmount;

    /**
     * Trạng thái đơn hàng
     * Có thể là: PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED
     */
    private String status;

    /**
     * Địa chỉ giao hàng
     */
    private String shippingAddress;

    /**
     * Số điện thoại người nhận
     */
    private String phoneNumber;

    /**
     * Ghi chú đơn hàng
     */
    private String note;

    /**
     * Thời gian tạo đơn hàng
     */
    private LocalDateTime createdAt;

    /**
     * Thời gian cập nhật đơn hàng
     */
    private LocalDateTime updatedAt;

    /**
     * Tự động cập nhật thời gian tạo và cập nhật trước khi lưu
     */
    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
} 