package com.myapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "don_hang")
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private User user;

    @Column(name = "ngay_dat_hang", nullable = false)
    private LocalDateTime ngayDatHang;

    @Column(name = "ngay_giao_hang")
    private LocalDateTime ngayGiaoHang;

    @Column(name = "ngay_nhan_hang")
    private LocalDateTime ngayNhanHang;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @Column(name = "tong_tien", nullable = false, precision = 10, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "tong_thanh_toan", nullable = false, precision = 10, scale = 2)
    private BigDecimal tongThanhToan;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "so_dien_thoai", nullable = false)
    private String soDienThoai;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @Column(name = "tinh_thanh", nullable = false)
    private String tinhThanh;

    @Column(name = "quan_huyen", nullable = false)
    private String quanHuyen;

    @Column(name = "phuong_xa", nullable = false)
    private String phuongXa;

    @Column(name = "ma_buu_dien")
    private String maBuuDien;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietDonHang> chiTietDonHangs;
} 