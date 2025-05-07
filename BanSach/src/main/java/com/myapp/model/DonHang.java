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

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayDatHang;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayGiaoHang;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayNhanHang;

    @Column(nullable = false, length = 45)
    private String trangThai;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tongTien;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tongThanhToan;

    @Column(length = 256)
    private String ghiChu;

    @Column(nullable = false, length = 45)
    private String hoTen;

    @Column(nullable = false, length = 15)
    private String soDienThoai;

    @Column(nullable = false, length = 64)
    private String diaChi;

    @Column(nullable = false, length = 45)
    private String tinhThanh;

    @Column(nullable = false, length = 45)
    private String quanHuyen;

    @Column(nullable = false, length = 45)
    private String phuongXa;

    @Column(length = 10)
    private String maBuuDien;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietDonHang> chiTietDonHangs;
} 