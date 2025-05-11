package com.myapp.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Lớp VaiTro đại diện cho các vai trò trong hệ thống
 * Sử dụng Lombok @Data để tự động tạo các phương thức getter, setter, equals, hashCode và toString
 * 
 * @Entity - Đánh dấu đây là một entity JPA
 * @Table - Chỉ định tên bảng trong cơ sở dữ liệu là "vai_tro"
 */
@Data
@Entity
@Table(name = "vai_tro")
public class VaiTro {
    /**
     * ID của vai trò, được tự động tăng
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Tên của vai trò, không được trùng lặp và không được để trống
     * Ví dụ: ROLE_ADMIN, ROLE_USER, ROLE_STAFF
     */
    @Column(name = "ten_vai_tro", nullable = false, unique = true)
    private String tenVaiTro;

    /**
     * Mô tả chi tiết về vai trò
     * Giải thích chức năng và quyền hạn của vai trò trong hệ thống
     */
    @Column(name = "mo_ta")
    private String moTa;
} 