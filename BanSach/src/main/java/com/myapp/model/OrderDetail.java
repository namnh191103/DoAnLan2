package com.myapp.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity đại diện cho chi tiết đơn hàng trong hệ thống
 * 
 * Sử dụng annotation @Entity để đánh dấu đây là một JPA Entity
 * @Table: Chỉ định tên bảng trong database
 * @Data: Tự động tạo getter, setter, toString, equals, hashCode
 */
@Entity
@Table(name = "order_details")
@Data
public class OrderDetail {
    /**
     * ID của chi tiết đơn hàng
     * @Id: Đánh dấu đây là khóa chính
     * @GeneratedValue: Tự động tăng giá trị
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Đơn hàng chứa chi tiết này
     * @ManyToOne: Quan hệ nhiều-1 với Order
     * @JoinColumn: Chỉ định tên cột khóa ngoại
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Sản phẩm trong chi tiết đơn hàng
     * @ManyToOne: Quan hệ nhiều-1 với Product
     * @JoinColumn: Chỉ định tên cột khóa ngoại
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Số lượng sản phẩm
     */
    private Integer quantity;

    /**
     * Giá sản phẩm tại thời điểm đặt hàng
     */
    private Double price;

    /**
     * Tổng tiền cho chi tiết đơn hàng này
     */
    private Double subtotal;
} 