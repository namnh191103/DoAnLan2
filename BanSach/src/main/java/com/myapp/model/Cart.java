package com.myapp.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Lớp Cart đại diện cho một item trong giỏ hàng của người dùng
 * Sử dụng các annotation của Lombok:
 * - @Data: tự động tạo getter, setter, equals, hashCode và toString
 * - @Builder: cho phép sử dụng Builder pattern
 * - @NoArgsConstructor: tạo constructor không tham số
 * - @AllArgsConstructor: tạo constructor với tất cả tham số
 * 
 * @Entity - Đánh dấu đây là một entity JPA
 * @Table - Chỉ định tên bảng trong cơ sở dữ liệu là "gio_hang_item"
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gio_hang_item")
public class Cart {
    /**
     * ID của item trong giỏ hàng, được tự động tăng
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Quan hệ nhiều-một với bảng User
     * Mỗi item trong giỏ hàng thuộc về một người dùng
     * FetchType.LAZY: chỉ tải dữ liệu khi cần thiết
     * Không được phép null
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private User user;

    /**
     * Quan hệ nhiều-một với bảng Product
     * Mỗi item trong giỏ hàng liên quan đến một sản phẩm
     * FetchType.LAZY: chỉ tải dữ liệu khi cần thiết
     * Không được phép null
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tac_pham_id", nullable = false)
    private Product product;

    /**
     * Số lượng sản phẩm trong giỏ hàng
     * Không được phép null
     */
    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;
} 