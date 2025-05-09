package com.myapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tac_pham")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "tua_de", nullable = false)
    private String tuaDe;
    
    @Column(name = "duong_dan", nullable = false, unique = true)
    private String duongDan;
    
    @Column(name = "tom_tat", nullable = false, columnDefinition = "nvarchar(max)")
    private String tomTat;
    
    @Column(name = "noi_dung", nullable = false, columnDefinition = "nvarchar(max)")
    private String noiDung;
    
    @Column(name = "ngay_xuat_ban")
    private LocalDate ngayXuatBan;
    
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
    
    @Column(name = "da_xuat_ban", nullable = false)
    private Boolean daXuatBan = false;
    
    @Column(name = "so_luong_ton")
    private Integer soLuongTon;
    
    @Column(name = "gia_nhap", precision = 10, scale = 2)
    private BigDecimal giaNhap;
    
    @Column(name = "gia_ban", precision = 10, scale = 2)
    private BigDecimal giaBan;
    
    @Column(name = "phan_tram_giam_gia", precision = 5, scale = 2)
    private BigDecimal phanTramGiamGia;
    
    @Column(name = "anh_bia", nullable = false)
    private String anhBia;
    
    @Column(name = "review_count")
    private Integer reviewCount = 0;
    
    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating;
    
    @Column(name = "length_cm", precision = 5, scale = 2)
    private BigDecimal lengthCm;
    
    @Column(name = "width_cm", precision = 5, scale = 2)
    private BigDecimal widthCm;
    
    @Column(name = "height_cm", precision = 5, scale = 2)
    private BigDecimal heightCm;
    
    @Column(name = "weight_kg", precision = 5, scale = 2)
    private BigDecimal weightKg;
} 