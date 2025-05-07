package com.myapp.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietDonHangDTO {
    private Integer id;
    private Integer donHangId;
    private Integer tacPhamId;
    private Integer soLuong;
    private BigDecimal giaBan;
} 