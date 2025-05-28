package com.myapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * Lớp ChiTietDonHang đại diện cho chi tiết của một đơn hàng
 * Sử dụng các annotation của Lombok:
 * - @Data: tự động tạo getter, setter, equals, hashCode và toString
 * - @Builder: cho phép sử dụng Builder pattern
 * - @NoArgsConstructor: tạo constructor không tham số
 * - @AllArgsConstructor: tạo constructor với tất cả tham số
 * 
 * @Entity - Đánh dấu đây là một entity JPA
 * @Table - Chỉ định tên bảng trong cơ sở dữ liệu là "chi_tiet_don_hang"
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chi_tiet_don_hang")
public class ChiTietDonHang {
    /**
     * ID của chi tiết đơn hàng, được tự động tăng
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Khóa chính, tự động tăng

    /**
     * Quan hệ nhiều-một với bảng DonHang
     * Mỗi chi tiết đơn hàng thuộc về một đơn hàng
     * FetchType.LAZY: chỉ tải dữ liệu khi cần thiết
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_hang_id")
    private DonHang donHang; // Mỗi chi tiết đơn hàng phải gắn với một đơn hàng

    /**
     * Quan hệ nhiều-một với bảng Product
     * Mỗi chi tiết đơn hàng liên quan đến một sản phẩm
     * FetchType.LAZY: chỉ tải dữ liệu khi cần thiết
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // Mỗi chi tiết đơn hàng phải gắn với một sản phẩm

    /**
     * Số lượng sản phẩm trong chi tiết đơn hàng
     */
    @Column(name = "so_luong")
    private Integer soLuong; // Số lượng sản phẩm, nên kiểm tra không âm và > 0

    /**
     * Đơn giá của sản phẩm tại thời điểm đặt hàng
     */
    @Column(name = "don_gia")
    private Double donGia; // Đơn giá tại thời điểm đặt hàng, nên kiểm tra không âm
} 