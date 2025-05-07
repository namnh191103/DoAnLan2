package com.myapp.repository;

import com.myapp.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    Optional<KhachHang> findByEmail(String email);
} 