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

    @Column(nullable = false, length = 256)
    private String tuaDe;

    @Column(nullable = false, length = 256, unique = true)
    private String duongDan;

    @Column(nullable = false, columnDefinition = "nvarchar(max)")
    private String tomTat;

    @Column(nullable = false, columnDefinition = "nvarchar(max)")
    private String noiDung;

    @Temporal(TemporalType.DATE)
    private Date ngayXuatBan;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayCapNhat;

    @Column(nullable = false)
    private Boolean daXuatBan;

    private Integer soLuongTon;

    private BigDecimal giaNhap;

    private BigDecimal giaBan;

    private BigDecimal phanTramGiamGia;

    @Column(nullable = false, length = 256)
    private String anhBia;

    private Integer reviewCount;

    private BigDecimal averageRating;

    private BigDecimal lengthCm;
    private BigDecimal widthCm;
    private BigDecimal heightCm;
    private BigDecimal weightKg;

    @OneToMany(mappedBy = "tacPham", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietDonHang> chiTietDonHangs;
} 