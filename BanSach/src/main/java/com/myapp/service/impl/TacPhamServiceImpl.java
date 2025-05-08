package com.myapp.service.impl;

import com.myapp.dto.TacPhamDTO;
import com.myapp.model.TacPham;
import com.myapp.repository.TacPhamRepository;
import com.myapp.service.serviceinterface.TacPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TacPhamServiceImpl implements TacPhamService {
    @Autowired
    private TacPhamRepository repository;

    private TacPhamDTO toDTO(TacPham entity) {
        return TacPhamDTO.builder()
                .id(entity.getId())
                .tuaDe(entity.getTuaDe())
                .duongDan(entity.getDuongDan())
                .tomTat(entity.getTomTat())
                .noiDung(entity.getNoiDung())
                .ngayXuatBan(entity.getNgayXuatBan())
                .ngayCapNhat(entity.getNgayCapNhat())
                .daXuatBan(entity.getDaXuatBan())
                .soLuongTon(entity.getSoLuongTon())
                .giaNhap(entity.getGiaNhap())
                .giaBan(entity.getGiaBan())
                .phanTramGiamGia(entity.getPhanTramGiamGia())
                .anhBia(entity.getAnhBia())
                .reviewCount(entity.getReviewCount())
                .averageRating(entity.getAverageRating())
                .lengthCm(entity.getLengthCm())
                .widthCm(entity.getWidthCm())
                .heightCm(entity.getHeightCm())
                .weightKg(entity.getWeightKg())
                .build();
    }

    private TacPham toEntity(TacPhamDTO dto) {
        return TacPham.builder()
                .id(dto.getId())
                .tuaDe(dto.getTuaDe())
                .duongDan(dto.getDuongDan())
                .tomTat(dto.getTomTat())
                .noiDung(dto.getNoiDung())
                .ngayXuatBan(dto.getNgayXuatBan())
                .ngayCapNhat(dto.getNgayCapNhat())
                .daXuatBan(dto.getDaXuatBan())
                .soLuongTon(dto.getSoLuongTon())
                .giaNhap(dto.getGiaNhap())
                .giaBan(dto.getGiaBan())
                .phanTramGiamGia(dto.getPhanTramGiamGia())
                .anhBia(dto.getAnhBia())
                .reviewCount(dto.getReviewCount())
                .averageRating(dto.getAverageRating())
                .lengthCm(dto.getLengthCm())
                .widthCm(dto.getWidthCm())
                .heightCm(dto.getHeightCm())
                .weightKg(dto.getWeightKg())
                .build();
    }

    @Override
    public TacPhamDTO getTacPhamById(Integer id) {
        return repository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<TacPhamDTO> getAllTacPham() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public TacPhamDTO createTacPham(TacPhamDTO dto) {
        TacPham entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Override
    public TacPhamDTO updateTacPham(Integer id, TacPhamDTO dto) {
        Optional<TacPham> optional = repository.findById(id);
        if (optional.isPresent()) {
            TacPham entity = optional.get();
            entity.setTuaDe(dto.getTuaDe());
            entity.setDuongDan(dto.getDuongDan());
            entity.setTomTat(dto.getTomTat());
            entity.setNoiDung(dto.getNoiDung());
            entity.setNgayXuatBan(dto.getNgayXuatBan());
            entity.setNgayCapNhat(dto.getNgayCapNhat());
            entity.setDaXuatBan(dto.getDaXuatBan());
            entity.setSoLuongTon(dto.getSoLuongTon());
            entity.setGiaNhap(dto.getGiaNhap());
            entity.setGiaBan(dto.getGiaBan());
            entity.setPhanTramGiamGia(dto.getPhanTramGiamGia());
            entity.setAnhBia(dto.getAnhBia());
            entity.setReviewCount(dto.getReviewCount());
            entity.setAverageRating(dto.getAverageRating());
            entity.setLengthCm(dto.getLengthCm());
            entity.setWidthCm(dto.getWidthCm());
            entity.setHeightCm(dto.getHeightCm());
            entity.setWeightKg(dto.getWeightKg());
            return toDTO(repository.save(entity));
        }
        return null;
    }

    @Override
    public void deleteTacPham(Integer id) {
        repository.deleteById(id);
    }
} 