package com.myapp.service.impl;

import com.myapp.dto.DonHangDTO;
import com.myapp.dto.ChiTietDonHangDTO;
import com.myapp.model.DonHang;
import com.myapp.model.User;
import com.myapp.model.TrangThaiDonHang;
import com.myapp.repository.DonHangRepository;
import com.myapp.repository.UserRepository;
import com.myapp.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DonHangServiceImpl implements DonHangService {
    private final DonHangRepository repository;
    private final UserRepository userRepository;

    @Override
    public DonHangDTO findById(Integer id) {
        return repository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public Page<DonHangDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    @Override
    @Transactional
    public DonHangDTO create(DonHangDTO dto) {
        DonHang entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public DonHangDTO update(Integer id, DonHangDTO dto) {
        Optional<DonHang> optional = repository.findById(id);
        if (optional.isPresent()) {
            DonHang entity = optional.get();
            updateEntityFromDTO(entity, dto);
            return toDTO(repository.save(entity));
        }
        return null;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<DonHangDTO> findByUserId(Integer userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DonHangDTO> findByStatus(String status, Pageable pageable) {
        return repository.findByTrangThai(status, pageable).map(this::toDTO);
    }

    @Override
    public DashboardStats getDashboardStats() {
        TrangThaiDonHang statusHoanThanh = TrangThaiDonHang.DA_GIAO;
        LocalDate fromDate = LocalDate.now().minusDays(6); // 7 ngày gần nhất (bao gồm hôm nay)
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        System.out.println("[DEBUG] statusHoanThanh (enum): " + statusHoanThanh);
        System.out.println("[DEBUG] fromDateTime: " + fromDateTime);
        // Log số lượng đơn hàng ĐÃ_GIAO thực tế
        var donHangList = repository.findByTrangThai(statusHoanThanh);
        System.out.println("[DEBUG] Số đơn ĐÃ_GIAO lấy được từ repository: " + donHangList.size());
        for (com.myapp.model.DonHang dh : donHangList) {
            System.out.println("[DEBUG] DonHang id=" + dh.getId() + ", ngayDatHang=" + dh.getNgayDatHang() + ", tongThanhToan=" + dh.getTongThanhToan());
        }
        BigDecimal tongDoanhThu = repository.sumTongThanhToanByStatus(statusHoanThanh);
        System.out.println("[DEBUG] Tổng doanh thu ĐÃ_GIAO từ repository: " + tongDoanhThu);
        long tongSoDon = repository.countAllOrders();
        long soDonHoanThanh = repository.countByStatus(statusHoanThanh);
        java.util.List<java.util.Map<String, Object>> doanhThu7Ngay = repository.getRevenueByDay(statusHoanThanh, fromDateTime);
        System.out.println("[DEBUG] doanhThu7Ngay raw from repo: " + doanhThu7Ngay);
        // Đảm bảo đủ 7 ngày, kể cả ngày không có đơn
        Map<LocalDate, BigDecimal> map = new HashMap<>();
        for (java.util.Map<String, Object> row : doanhThu7Ngay) {
            Object ngayObj = row.get("ngay");
            LocalDate ngay;
            if (ngayObj instanceof LocalDate) {
                ngay = (LocalDate) ngayObj;
            } else if (ngayObj instanceof java.sql.Date) {
                ngay = ((java.sql.Date) ngayObj).toLocalDate();
            } else if (ngayObj instanceof LocalDateTime) {
                ngay = ((LocalDateTime) ngayObj).toLocalDate();
            } else {
                ngay = LocalDate.parse(ngayObj.toString());
            }
            BigDecimal doanhThu = (BigDecimal) row.get("doanhThu");
            map.put(ngay, doanhThu);
        }
        java.util.List<java.util.Map<String, Object>> result = new java.util.ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate d = LocalDate.now().minusDays(i);
            BigDecimal v = map.getOrDefault(d, BigDecimal.ZERO);
            Map<String, Object> item = new HashMap<>();
            item.put("ngay", d.toString());
            item.put("doanhThu", v);
            result.add(item);
        }
        System.out.println("[DEBUG] Doanh thu 7 ngày (result): " + result);
        DashboardStats stats = new DashboardStats() {
            @Override
            public BigDecimal getTongDoanhThu() { return tongDoanhThu; }
            @Override
            public long getTongSoDon() { return tongSoDon; }
            @Override
            public long getSoDonHoanThanh() { return soDonHoanThanh; }
            @Override
            public java.util.List<java.util.Map<String, Object>> getDoanhThu7Ngay() { return result; }
        };
        return stats;
    }

    private DonHangDTO toDTO(DonHang entity) {
        List<ChiTietDonHangDTO> chiTietDonHangs = entity.getChiTietDonHangs().stream()
                .map(ct -> ChiTietDonHangDTO.builder()
                        .id(ct.getId())
                        .donHang(ct.getDonHang())
                        .productId(ct.getProduct().getId())
                        .soLuong(ct.getSoLuong())
                        .donGia(ct.getDonGia())
                        .build())
                .collect(Collectors.toList());

        return DonHangDTO.builder()
                .id(entity.getId())
                .khachHangId(entity.getUser().getId())
                .ngayDatHang(entity.getNgayDatHang())
                .ngayGiaoHang(entity.getNgayGiaoHang())
                .ngayNhanHang(entity.getNgayNhanHang())
                .trangThai(entity.getTrangThai())
                .tongTien(entity.getTongTien())
                .tongThanhToan(entity.getTongThanhToan())
                .ghiChu(entity.getGhiChu())
                .hoTen(entity.getHoTen())
                .soDienThoai(entity.getSoDienThoai())
                .diaChi(entity.getDiaChi())
                .tinhThanh(entity.getTinhThanh())
                .quanHuyen(entity.getQuanHuyen())
                .phuongXa(entity.getPhuongXa())
                .maBuuDien(entity.getMaBuuDien())
                .chiTietDonHangs(chiTietDonHangs)
                .build();
    }

    private DonHang toEntity(DonHangDTO dto) {
        DonHang entity = DonHang.builder()
                .id(dto.getId())
                .ngayDatHang(dto.getNgayDatHang())
                .ngayGiaoHang(dto.getNgayGiaoHang())
                .ngayNhanHang(dto.getNgayNhanHang())
                .trangThai(dto.getTrangThai())
                .tongTien(dto.getTongTien())
                .tongThanhToan(dto.getTongThanhToan())
                .ghiChu(dto.getGhiChu())
                .hoTen(dto.getHoTen())
                .soDienThoai(dto.getSoDienThoai())
                .diaChi(dto.getDiaChi())
                .tinhThanh(dto.getTinhThanh())
                .quanHuyen(dto.getQuanHuyen())
                .phuongXa(dto.getPhuongXa())
                .maBuuDien(dto.getMaBuuDien())
                .build();

        if (dto.getKhachHangId() != null) {
            Optional<User> user = userRepository.findById(dto.getKhachHangId());
            user.ifPresent(entity::setUser);
        }

        return entity;
    }

    private void updateEntityFromDTO(DonHang entity, DonHangDTO dto) {
        if (dto.getNgayDatHang() != null) {
            entity.setNgayDatHang(dto.getNgayDatHang());
        }
        entity.setNgayGiaoHang(dto.getNgayGiaoHang());
        entity.setNgayNhanHang(dto.getNgayNhanHang());
        entity.setTrangThai(dto.getTrangThai());
        if (dto.getTongTien() != null) {
            entity.setTongTien(dto.getTongTien());
        }
        if (dto.getTongThanhToan() != null) {
            entity.setTongThanhToan(dto.getTongThanhToan());
        }
        entity.setGhiChu(dto.getGhiChu());
        entity.setHoTen(dto.getHoTen());
        entity.setSoDienThoai(dto.getSoDienThoai());
        entity.setDiaChi(dto.getDiaChi());
        entity.setTinhThanh(dto.getTinhThanh());
        entity.setQuanHuyen(dto.getQuanHuyen());
        entity.setPhuongXa(dto.getPhuongXa());
        entity.setMaBuuDien(dto.getMaBuuDien());
        if (dto.getKhachHangId() != null) {
            Optional<User> user = userRepository.findById(dto.getKhachHangId());
            user.ifPresent(entity::setUser);
        }
    }
} 