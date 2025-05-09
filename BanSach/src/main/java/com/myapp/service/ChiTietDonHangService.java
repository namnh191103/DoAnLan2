package com.myapp.service;

import com.myapp.dto.ChiTietDonHangDTO;
import com.myapp.model.DonHang;

import java.util.List;

public interface ChiTietDonHangService {
    ChiTietDonHangDTO create(ChiTietDonHangDTO chiTietDonHangDTO);
    ChiTietDonHangDTO findById(Integer id);
    List<ChiTietDonHangDTO> findByDonHang(DonHang donHang);
    ChiTietDonHangDTO update(Integer id, ChiTietDonHangDTO chiTietDonHangDTO);
    void delete(Integer id);
} 