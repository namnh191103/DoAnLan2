package com.myapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lớp SanPham đại diện cho một sản phẩm trong hệ thống
 * Sử dụng các annotation của Lombok:
 * - @Data: tự động tạo getter, setter, equals, hashCode và toString
 * - @NoArgsConstructor: tạo constructor không tham số
 * - @AllArgsConstructor: tạo constructor với tất cả tham số
 * 
 * @Entity - Đánh dấu đây là một entity JPA
 * @Table - Chỉ định tên bảng trong cơ sở dữ liệu là "san_pham"
 */
@Entity
@Table(name = "san_pham")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {
    /**
     * ID của sản phẩm, được tự động tăng
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tên của sản phẩm
     */
    private String ten;

    /**
     * Mô tả chi tiết về sản phẩm
     */
    private String moTa;

    /**
     * Giá bán của sản phẩm
     */
    private double gia;

    /**
     * Số lượng tồn kho của sản phẩm
     */
    private int soLuong;

    /**
     * Đường dẫn đến hình ảnh của sản phẩm
     */
    private String hinhAnh;
} 