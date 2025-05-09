package com.myapp.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHangDTO {
    private Integer id;
    private Integer khachHangId;
    private LocalDateTime ngayDatHang;
    private LocalDateTime ngayGiaoHang;
    private LocalDateTime ngayNhanHang;
    private String trangThai;
    private BigDecimal tongTien;
    private BigDecimal tongThanhToan;
    private String ghiChu;
    private String hoTen;
    private String soDienThoai;
    private String diaChi;
    private String tinhThanh;
    private String quanHuyen;
    private String phuongXa;
    private String maBuuDien;
    private List<ChiTietDonHangDTO> chiTietDonHangs;
} 