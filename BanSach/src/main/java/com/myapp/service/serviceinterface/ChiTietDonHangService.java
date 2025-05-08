package com.myapp.service.serviceinterface;

import com.myapp.dto.ChiTietDonHangDTO;
import java.util.List;

public interface ChiTietDonHangService {
    ChiTietDonHangDTO getChiTietDonHangById(Integer id);
    List<ChiTietDonHangDTO> getAllChiTietDonHang();
    ChiTietDonHangDTO createChiTietDonHang(ChiTietDonHangDTO dto);
    ChiTietDonHangDTO updateChiTietDonHang(Integer id, ChiTietDonHangDTO dto);
    void deleteChiTietDonHang(Integer id);
} 