package com.myapp.service.impl;

import com.myapp.dto.DonHangDTO;
import com.myapp.dto.ChiTietDonHangDTO;
import com.myapp.model.DonHang;
import com.myapp.model.User;
import com.myapp.repository.DonHangRepository;
import com.myapp.repository.UserRepository;
import com.myapp.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        entity.setNgayDatHang(dto.getNgayDatHang());
        entity.setNgayGiaoHang(dto.getNgayGiaoHang());
        entity.setNgayNhanHang(dto.getNgayNhanHang());
        entity.setTrangThai(dto.getTrangThai());
        entity.setTongTien(dto.getTongTien());
        entity.setTongThanhToan(dto.getTongThanhToan());
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