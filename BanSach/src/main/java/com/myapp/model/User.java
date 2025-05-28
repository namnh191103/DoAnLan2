package com.myapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;
import java.io.Serializable;

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
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
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
    private String email; // Nên kiểm tra định dạng email ở tầng service/controller để tránh dữ liệu không hợp lệ
    
    /**
     * Mật khẩu của người dùng, không được để trống
     */
    @Column(nullable = false)
    private String matKhau; // Cảnh báo: Mật khẩu nên được mã hóa (hash) trước khi lưu vào DB, không lưu plain text
    
    /**
     * Họ tên đầy đủ của người dùng, không được để trống
     */
    @Column(nullable = false)
    private String hoTen; // Có thể bổ sung kiểm tra độ dài hoặc ký tự đặc biệt nếu cần
    
    /**
     * Số điện thoại của người dùng, không được để trống
     */
    @Column(nullable = false)
    private String soDienThoai; // Nên kiểm tra định dạng số điện thoại ở tầng service/controller
    
    /**
     * Đường dẫn đến hình ảnh đại diện của người dùng
     */
    @Column
    private String hinhAnh; // Có thể null, nên kiểm tra đường dẫn hợp lệ nếu sử dụng
    
    /**
     * Thời điểm tạo tài khoản, không được để trống
     */
    @Column(nullable = false)
    private LocalDateTime ngayTao; // Trường này nên được set tự động khi tạo user mới
    
    /**
     * Trạng thái kích hoạt tài khoản, không được để trống
     */
    @Column(nullable = false)
    private Boolean daKichHoat = false; // Đánh dấu tài khoản đã kích hoạt hay chưa (qua email, SMS...)
    
    /**
     * Phương thức xác thực tài khoản, không được để trống
     */
    @Column(nullable = false)
    private String loaiXacThuc; // Nên dùng Enum thay vì String để tránh lỗi chính tả hoặc giá trị không hợp lệ
    
    /**
     * Mã xác thực cho việc kích hoạt tài khoản hoặc đặt lại mật khẩu
     */
    @Column
    private String maXacThuc; // Dùng cho xác thực email hoặc reset mật khẩu, nên có thời hạn hiệu lực

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
    private Set<VaiTro> vaiTros; // Một user có thể có nhiều vai trò (admin, user, staff...)

    /**
     * Thời gian hết hạn của mã xác thực OTP (dùng cho quên mật khẩu)
     */
    @Column
    private LocalDateTime otpExpiredAt; // Có thể null, chỉ set khi gửi OTP quên mật khẩu

    public boolean isDaKhoa() {
        return daKhoa;
    }

    public void setDaKhoa(boolean daKhoa) {
        this.daKhoa = daKhoa;
    }
} 