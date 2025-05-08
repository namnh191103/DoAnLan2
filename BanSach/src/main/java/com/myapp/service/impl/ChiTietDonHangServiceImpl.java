package com.myapp.service.impl;

import com.myapp.dto.ChiTietDonHangDTO;
import com.myapp.model.ChiTietDonHang;
import com.myapp.repository.ChiTietDonHangRepository;
import com.myapp.service.serviceinterface.ChiTietDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    @Autowired
    private ChiTietDonHangRepository repository;

    private ChiTietDonHangDTO toDTO(ChiTietDonHang entity) {
        return ChiTietDonHangDTO.builder()
                .id(entity.getId())
                .donHangId(entity.getDonHang().getId())
                .tacPhamId(entity.getTacPham().getId())
                .soLuong(entity.getSoLuong())
                .giaBan(entity.getGiaBan())
                .build();
    }

    private ChiTietDonHang toEntity(ChiTietDonHangDTO dto) {
        // Chỉ map các trường cơ bản, các trường liên kết cần xử lý ở controller/service cao hơn
        return ChiTietDonHang.builder()
                .id(dto.getId())
                .soLuong(dto.getSoLuong())
                .giaBan(dto.getGiaBan())
                .build();
    }

    @Override
    public ChiTietDonHangDTO getChiTietDonHangById(Integer id) {
        return repository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<ChiTietDonHangDTO> getAllChiTietDonHang() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ChiTietDonHangDTO createChiTietDonHang(ChiTietDonHangDTO dto) {
        ChiTietDonHang entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Override
    public ChiTietDonHangDTO updateChiTietDonHang(Integer id, ChiTietDonHangDTO dto) {
        Optional<ChiTietDonHang> optional = repository.findById(id);
        if (optional.isPresent()) {
            ChiTietDonHang entity = optional.get();
            entity.setSoLuong(dto.getSoLuong());
            entity.setGiaBan(dto.getGiaBan());
            return toDTO(repository.save(entity));
        }
        return null;
    }

    @Override
    public void deleteChiTietDonHang(Integer id) {
        repository.deleteById(id);
    }
} 