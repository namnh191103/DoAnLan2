// Khai báo package cho repository quản lý chi tiết đơn hàng
package com.myapp.repository;

import com.myapp.model.ChiTietDonHang; // Import entity ChiTietDonHang
import com.myapp.model.DonHang; // Import entity DonHang
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository để thao tác với database
import org.springframework.stereotype.Repository; // Đánh dấu đây là một repository
import java.util.List; // Thư viện danh sách

@Repository // Đánh dấu đây là một repository (nơi quản lý dữ liệu)
public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, Integer> { // Kế thừa JpaRepository để có sẵn các hàm CRUD
    List<ChiTietDonHang> findByDonHang(DonHang donHang); // Tìm danh sách chi tiết đơn hàng theo đơn hàng
} 