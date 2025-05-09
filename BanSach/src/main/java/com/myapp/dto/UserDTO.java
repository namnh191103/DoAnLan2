package com.myapp.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Integer id;
    private String email;
    private String matKhau;
    private String hoTen;
    private String soDienThoai;
    private String hinhAnh;
    private LocalDateTime ngayTao;
    private Boolean daKichHoat;
    private String loaiXacThuc;
    private String maXacThuc;
} 