package com.myapp.dto;

import com.myapp.model.DonHang;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDonHangDTO {
    private Integer id;
    private DonHang donHang;
    private Integer productId;
    private Integer soLuong;
    private Double donGia;
} 