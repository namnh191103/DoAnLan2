package com.myapp.service.serviceinterface;

import com.myapp.dto.DonHangDTO;
import java.util.List;

public interface DonHangService {
    DonHangDTO getDonHangById(Integer id);
    List<DonHangDTO> getAllDonHang();
    DonHangDTO createDonHang(DonHangDTO dto);
    DonHangDTO updateDonHang(Integer id, DonHangDTO dto);
    void deleteDonHang(Integer id);
} 