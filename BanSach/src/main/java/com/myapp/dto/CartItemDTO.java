package com.myapp.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Integer id;
    private Integer tacPhamId;
    private String tuaDe;
    private BigDecimal giaBan;
    private BigDecimal phanTramGiamGia;
    private Integer soLuong;
    private String anhBia;
    private BigDecimal thanhTien;
    private String userEmail;
} 