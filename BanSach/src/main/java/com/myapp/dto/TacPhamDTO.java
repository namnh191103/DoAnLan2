package com.myapp.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TacPhamDTO {
    private Integer id;
    private String tuaDe;
    private String duongDan;
    private String tomTat;
    private String noiDung;
    private Date ngayXuatBan;
    private Date ngayCapNhat;
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