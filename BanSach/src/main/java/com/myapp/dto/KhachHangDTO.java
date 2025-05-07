package com.myapp.dto;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHangDTO {
    private Integer id;
    private String email;
    private String hoTen;
    private String soDienThoai;
    private Date ngayTao;
    private Boolean daKichHoat;
    private String loaiXacThuc;
    private String maXacThuc;
} 