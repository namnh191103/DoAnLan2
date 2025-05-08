package com.myapp.service.impl;

import com.myapp.dto.KhachHangDTO;
import com.myapp.model.KhachHang;
import com.myapp.repository.KhachHangRepository;
import com.myapp.service.serviceinterface.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangRepository repository;

    private KhachHangDTO toDTO(KhachHang entity) {
        return KhachHangDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .hoTen(entity.getHoTen())
                .soDienThoai(entity.getSoDienThoai())
                .ngayTao(entity.getNgayTao())
                .daKichHoat(entity.getDaKichHoat())
                .loaiXacThuc(entity.getLoaiXacThuc())
                .maXacThuc(entity.getMaXacThuc())
                .build();
    }

    private KhachHang toEntity(KhachHangDTO dto) {
        return KhachHang.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .hoTen(dto.getHoTen())
                .soDienThoai(dto.getSoDienThoai())
                .ngayTao(dto.getNgayTao())
                .daKichHoat(dto.getDaKichHoat())
                .loaiXacThuc(dto.getLoaiXacThuc())
                .maXacThuc(dto.getMaXacThuc())
                .build();
    }

    @Override
    public KhachHangDTO getKhachHangById(Integer id) {
        return repository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<KhachHangDTO> getAllKhachHang() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public KhachHangDTO createKhachHang(KhachHangDTO dto) {
        KhachHang entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Override
    public KhachHangDTO updateKhachHang(Integer id, KhachHangDTO dto) {
        Optional<KhachHang> optional = repository.findById(id);
        if (optional.isPresent()) {
            KhachHang entity = optional.get();
            entity.setEmail(dto.getEmail());
            entity.setHoTen(dto.getHoTen());
            entity.setSoDienThoai(dto.getSoDienThoai());
            entity.setNgayTao(dto.getNgayTao());
            entity.setDaKichHoat(dto.getDaKichHoat());
            entity.setLoaiXacThuc(dto.getLoaiXacThuc());
            entity.setMaXacThuc(dto.getMaXacThuc());
            return toDTO(repository.save(entity));
        }
        return null;
    }

    @Override
    public void deleteKhachHang(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public KhachHangDTO login(String email, String matKhau) {
        Optional<KhachHang> optional = repository.findByEmail(email);
        if (optional.isPresent()) {
            KhachHang kh = optional.get();
            if (kh.getMatKhau().equals(matKhau)) { // Nếu có mã hóa thì dùng BCrypt ở đây
                return toDTO(kh);
            }
        }
        return null;
    }
} 