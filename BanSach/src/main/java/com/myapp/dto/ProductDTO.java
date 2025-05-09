package com.myapp.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProductDTO {
    private Integer id;
    private String tuaDe;
    private String duongDan;
    private String tomTat;
    private String noiDung;
    private LocalDate ngayXuatBan;
    private LocalDateTime ngayCapNhat;
    private Boolean daXuatBan;
    private Integer soLuongTon;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private BigDecimal phanTramGiamGia;
    private String anhBia;
    private Integer reviewCount;
    private BigDecimal averageRating;
    private BigDecimal lengthCm;
    private BigDecimal widthCm;
    private BigDecimal heightCm;
    private BigDecimal weightKg;
} 