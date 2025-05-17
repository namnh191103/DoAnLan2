package com.myapp.repository;

import com.myapp.model.DonHang;
import com.myapp.model.User;
import com.myapp.model.TrangThaiDonHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    List<DonHang> findByUser(User user);
    List<DonHang> findByUserId(Integer userId);
    Page<DonHang> findByTrangThai(String trangThai, Pageable pageable);
    List<DonHang> findByTrangThai(String trangThai);

    @Query("SELECT COALESCE(SUM(d.tongThanhToan),0) FROM DonHang d WHERE d.trangThai = :status")
    BigDecimal sumTongThanhToanByStatus(@Param("status") TrangThaiDonHang status);

    @Query("SELECT COUNT(d) FROM DonHang d WHERE d.trangThai = :status")
    long countByStatus(@Param("status") TrangThaiDonHang status);

    @Query("SELECT COUNT(d) FROM DonHang d")
    long countAllOrders();

    @Query("SELECT NEW map(CAST(d.ngayDatHang AS date) as ngay, COALESCE(SUM(d.tongThanhToan),0) as doanhThu) FROM DonHang d WHERE d.trangThai = :status AND d.ngayDatHang >= :fromDate GROUP BY CAST(d.ngayDatHang AS date) ORDER BY ngay DESC")
    java.util.List<java.util.Map<String, Object>> getRevenueByDay(@Param("status") TrangThaiDonHang status, @Param("fromDate") LocalDateTime fromDate);

    List<DonHang> findByTrangThai(TrangThaiDonHang trangThai);
} 