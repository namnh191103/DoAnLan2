package com.myapp.service.serviceinterface;

import com.myapp.dto.KhachHangDTO;
import java.util.List;

public interface KhachHangService {
    KhachHangDTO getKhachHangById(Integer id);
    List<KhachHangDTO> getAllKhachHang();
    KhachHangDTO createKhachHang(KhachHangDTO dto);
    KhachHangDTO updateKhachHang(Integer id, KhachHangDTO dto);
    void deleteKhachHang(Integer id);
} 