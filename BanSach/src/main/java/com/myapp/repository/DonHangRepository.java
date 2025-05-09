package com.myapp.repository;

import com.myapp.model.DonHang;
import com.myapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    List<DonHang> findByUser(User user);
    List<DonHang> findByUserId(Integer userId);
    Page<DonHang> findByTrangThai(String trangThai, Pageable pageable);
} 