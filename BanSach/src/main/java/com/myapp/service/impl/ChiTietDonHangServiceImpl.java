package com.myapp.service.impl;

import com.myapp.dto.ChiTietDonHangDTO;
import com.myapp.dto.ProductDTO;
import com.myapp.model.ChiTietDonHang;
import com.myapp.model.DonHang;
import com.myapp.model.Product;
import com.myapp.repository.ChiTietDonHangRepository;
import com.myapp.service.ChiTietDonHangService;
import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public ChiTietDonHangDTO create(ChiTietDonHangDTO chiTietDonHangDTO) {
        Product product = productService.findEntityById(chiTietDonHangDTO.getProductId());
        ChiTietDonHang chiTietDonHang = ChiTietDonHang.builder()
                .donHang(chiTietDonHangDTO.getDonHang())
                .product(product)
                .soLuong(chiTietDonHangDTO.getSoLuong())
                .donGia(chiTietDonHangDTO.getDonGia())
                .build();
        return convertToDTO(chiTietDonHangRepository.save(chiTietDonHang));
    }

    @Override
    public ChiTietDonHangDTO findById(Integer id) {
        return chiTietDonHangRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Chi tiết đơn hàng không tồn tại"));
    }

    @Override
    public List<ChiTietDonHangDTO> findByDonHang(DonHang donHang) {
        return chiTietDonHangRepository.findByDonHang(donHang).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChiTietDonHangDTO update(Integer id, ChiTietDonHangDTO chiTietDonHangDTO) {
        ChiTietDonHang chiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chi tiết đơn hàng không tồn tại"));
        Product product = productService.findEntityById(chiTietDonHangDTO.getProductId());
        
        chiTietDonHang.setProduct(product);
        chiTietDonHang.setSoLuong(chiTietDonHangDTO.getSoLuong());
        chiTietDonHang.setDonGia(chiTietDonHangDTO.getDonGia());
        
        return convertToDTO(chiTietDonHangRepository.save(chiTietDonHang));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        chiTietDonHangRepository.deleteById(id);
    }

    private ChiTietDonHangDTO convertToDTO(ChiTietDonHang chiTietDonHang) {
        return ChiTietDonHangDTO.builder()
                .id(chiTietDonHang.getId())
                .donHang(chiTietDonHang.getDonHang())
                .productId(chiTietDonHang.getProduct().getId())
                .soLuong(chiTietDonHang.getSoLuong())
                .donGia(chiTietDonHang.getDonGia())
                .build();
    }
} 