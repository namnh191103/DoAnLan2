package com.myapp.model;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "don_hang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private KhachHang khachHang;

    @Column(name = "ngay_dat_hang", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayDatHang;

    @Column(name = "ngay_giao_hang")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayGiaoHang;

    @Column(name = "ngay_nhan_hang")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayNhanHang;

    @Column(name = "trang_thai", nullable = false, length = 45)
    private String trangThai;

    @Column(name = "tong_tien", nullable = false, precision = 10, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "tong_thanh_toan", nullable = false, precision = 10, scale = 2)
    private BigDecimal tongThanhToan;

    @Column(name = "ghi_chu", length = 256)
    private String ghiChu;

    @Column(name = "ho_ten", nullable = false, length = 45)
    private String hoTen;

    @Column(name = "so_dien_thoai", nullable = false, length = 15)
    private String soDienThoai;

    @Column(name = "dia_chi", nullable = false, length = 64)
    private String diaChi;

    @Column(name = "tinh_thanh", nullable = false, length = 45)
    private String tinhThanh;

    @Column(name = "quan_huyen", nullable = false, length = 45)
    private String quanHuyen;

    @Column(name = "phuong_xa", nullable = false, length = 45)
    private String phuongXa;

    @Column(name = "ma_buu_dien", length = 10)
    private String maBuuDien;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietDonHang> chiTietDonHangs;
} 