package com.myapp.service;

import com.myapp.dto.DonHangDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DonHangService {
    DonHangDTO findById(Integer id);
    Page<DonHangDTO> findAll(Pageable pageable);
    DonHangDTO create(DonHangDTO dto);
    DonHangDTO update(Integer id, DonHangDTO dto);
    void delete(Integer id);
    List<DonHangDTO> findByUserId(Integer userId);
    Page<DonHangDTO> findByStatus(String status, Pageable pageable);

    // Thống kê dashboard cho admin
    interface DashboardStats {
        java.math.BigDecimal getTongDoanhThu();
        long getTongSoDon();
        long getSoDonHoanThanh();
        java.util.List<java.util.Map<String, Object>> getDoanhThu7Ngay();
    }
    DashboardStats getDashboardStats();
} 