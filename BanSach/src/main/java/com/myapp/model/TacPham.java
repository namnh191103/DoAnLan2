package com.myapp.model;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tac_pham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TacPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tua_de", nullable = false, length = 256)
    private String tuaDe;

    @Column(name = "duong_dan", nullable = false, length = 256, unique = true)
    private String duongDan;

    @Column(name = "tom_tat", nullable = false, columnDefinition = "nvarchar(max)")
    private String tomTat;

    @Column(name = "noi_dung", nullable = false, columnDefinition = "nvarchar(max)")
    private String noiDung;

    @Column(name = "ngay_xuat_ban")
    @Temporal(TemporalType.DATE)
    private Date ngayXuatBan;

    @Column(name = "ngay_cap_nhat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayCapNhat;

    @Column(name = "da_xuat_ban", nullable = false)
    private Boolean daXuatBan;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "gia_nhap")
    private BigDecimal giaNhap;

    @Column(name = "gia_ban")
    private BigDecimal giaBan;

    @Column(name = "phan_tram_giam_gia")
    private BigDecimal phanTramGiamGia;

    @Column(name = "anh_bia", nullable = false, length = 256)
    private String anhBia;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "average_rating")
    private BigDecimal averageRating;

    @Column(name = "length_cm")
    private BigDecimal lengthCm;

    @Column(name = "width_cm")
    private BigDecimal widthCm;

    @Column(name = "height_cm")
    private BigDecimal heightCm;

    @Column(name = "weight_kg")
    private BigDecimal weightKg;

    @OneToMany(mappedBy = "tacPham", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietDonHang> chiTietDonHangs;
} 