package com.myapp.dto;

import com.myapp.model.User;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) cho thông tin người dùng
 * 
 * Sử dụng annotation @Data để tự động tạo getter, setter, toString, equals, hashCode
 */
@Data
public class UserDTO {
    /**
     * ID của người dùng
     */
    private Integer id;

    /**
     * Email của người dùng
     */
    private String email;

    /**
     * Họ tên của người dùng
     */
    private String hoTen;

    /**
     * Số điện thoại của người dùng
     */
    private String soDienThoai;

    /**
     * Địa chỉ hoặc hình ảnh của người dùng
     */
    private String hinhAnh;

    /**
     * Ngày tạo của người dùng
     */
    private LocalDateTime ngayTao;

    /**
     * Trạng thái kích hoạt của người dùng
     */
    private Boolean daKichHoat;

    /**
     * Loại xác thực của người dùng
     */
    private String loaiXacThuc;

    /**
     * Mã xác thực của người dùng
     */
    private String maXacThuc;

    /**
     * Mật khẩu của người dùng
     */
    private String matKhau;

    /**
     * Trạng thái khóa của người dùng
     */
    private boolean daKhoa;

    /**
     * Danh sách tên vai trò/quyền của người dùng
     */
    private List<String> vaiTros;

    /**
     * Chuyển đổi từ User sang UserDTO
     * 
     * @param user Đối tượng User cần chuyển đổi
     * @return Đối tượng UserDTO mới
     */
    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setHoTen(user.getHoTen());
        dto.setSoDienThoai(user.getSoDienThoai());
        dto.setHinhAnh(user.getHinhAnh());
        dto.setNgayTao(user.getNgayTao());
        dto.setDaKichHoat(user.getDaKichHoat());
        dto.setLoaiXacThuc(user.getLoaiXacThuc());
        dto.setMaXacThuc(user.getMaXacThuc());
        dto.setMatKhau(user.getMatKhau());
        dto.setDaKhoa(user.isDaKhoa());
        if (user.getVaiTros() != null) {
            dto.setVaiTros(user.getVaiTros().stream().map(vt -> vt.getTenVaiTro()).collect(Collectors.toList()));
        }
        return dto;
    }

    /**
     * Lấy đối tượng User từ UserDTO
     * 
     * @return Đối tượng User tương ứng
     */
    public User getUser() {
        User user = new User();
        user.setId(this.id);
        user.setEmail(this.email);
        user.setHoTen(this.hoTen);
        user.setSoDienThoai(this.soDienThoai);
        user.setHinhAnh(this.hinhAnh);
        user.setNgayTao(this.ngayTao);
        user.setDaKichHoat(this.daKichHoat);
        user.setLoaiXacThuc(this.loaiXacThuc);
        user.setMaXacThuc(this.maXacThuc);
        user.setMatKhau(this.matKhau);
        return user;
    }
} 