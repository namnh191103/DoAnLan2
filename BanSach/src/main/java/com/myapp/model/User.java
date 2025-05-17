package com.myapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Lớp User đại diện cho thông tin người dùng trong hệ thống
 * Sử dụng Lombok @Data để tự động tạo các phương thức getter, setter, equals, hashCode và toString
 * 
 * @Entity - Đánh dấu đây là một entity JPA
 * @Table - Chỉ định tên bảng trong cơ sở dữ liệu là "khach_hang"
 */
@Data
@Entity
@Table(name = "khach_hang")
public class User {
    /**
     * ID của người dùng, được tự động tăng
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Email của người dùng, không được trùng lặp và không được để trống
     */
    @Column(nullable = false, unique = true)
    private String email;
    
    /**
     * Mật khẩu của người dùng, không được để trống
     */
    @Column(nullable = false)
    private String matKhau;
    
    /**
     * Họ tên đầy đủ của người dùng, không được để trống
     */
    @Column(nullable = false)
    private String hoTen;
    
    /**
     * Số điện thoại của người dùng, không được để trống
     */
    @Column(nullable = false)
    private String soDienThoai;
    
    /**
     * Đường dẫn đến hình ảnh đại diện của người dùng
     */
    @Column
    private String hinhAnh;
    
    /**
     * Thời điểm tạo tài khoản, không được để trống
     */
    @Column(nullable = false)
    private LocalDateTime ngayTao;
    
    /**
     * Trạng thái kích hoạt tài khoản, không được để trống
     */
    @Column(nullable = false)
    private Boolean daKichHoat = false;
    
    /**
     * Phương thức xác thực tài khoản, không được để trống
     */
    @Column(nullable = false)
    private String loaiXacThuc;
    
    /**
     * Mã xác thực cho việc kích hoạt tài khoản hoặc đặt lại mật khẩu
     */
    @Column
    private String maXacThuc;

    private boolean daKhoa = false; // Trường mới để đánh dấu tài khoản bị khóa

    /**
     * Danh sách các vai trò của người dùng
     * Quan hệ nhiều-nhiều với bảng VaiTro
     * Sử dụng bảng trung gian "khach_hang_vai_tro"
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "khach_hang_vai_tro",
        joinColumns = @JoinColumn(name = "khach_hang_id"),
        inverseJoinColumns = @JoinColumn(name = "vai_tro_id")
    )
    private Set<VaiTro> vaiTros;

    public boolean isDaKhoa() {
        return daKhoa;
    }

    public void setDaKhoa(boolean daKhoa) {
        this.daKhoa = daKhoa;
    }
} 