package com.myapp.service.impl;

import com.myapp.dto.ProductDTO;
import com.myapp.model.Product;
import com.myapp.repository.ProductRepository;
import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        Product product = new Product();
        updateProductFromDTO(product, productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    public ProductDTO findById(Integer id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    @Override
    public Product findEntityById(Integer id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public ProductDTO findByDuongDan(String duongDan) {
        Product product = productRepository.findByDuongDan(duongDan)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    @Override
    public ProductDTO findByTuaDe(String tuaDe) {
        Product product = productRepository.findByTuaDe(tuaDe)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(this::convertToDTO);
    }

    @Override
    public Page<ProductDTO> search(String keyword, Pageable pageable) {
        return productRepository.findByTuaDeContainingOrTomTatContaining(keyword, keyword, pageable)
            .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public ProductDTO update(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        updateProductFromDTO(product, productDTO);
        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

    private void updateProductFromDTO(Product product, ProductDTO dto) {
        product.setTuaDe(dto.getTuaDe());
        product.setDuongDan(dto.getDuongDan());
        product.setTomTat(dto.getTomTat());
        product.setNoiDung(dto.getNoiDung());
        product.setNgayXuatBan(dto.getNgayXuatBan());
        product.setNgayCapNhat(dto.getNgayCapNhat());
        product.setDaXuatBan(dto.getDaXuatBan());
        product.setSoLuongTon(dto.getSoLuongTon());
        product.setGiaNhap(dto.getGiaNhap());
        product.setGiaBan(dto.getGiaBan());
        product.setPhanTramGiamGia(dto.getPhanTramGiamGia());
        product.setAnhBia(dto.getAnhBia());
        product.setReviewCount(dto.getReviewCount());
        product.setAverageRating(dto.getAverageRating());
        product.setLengthCm(dto.getLengthCm());
        product.setWidthCm(dto.getWidthCm());
        product.setHeightCm(dto.getHeightCm());
        product.setWeightKg(dto.getWeightKg());
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTuaDe(product.getTuaDe());
        dto.setDuongDan(product.getDuongDan());
        dto.setTomTat(product.getTomTat());
        dto.setNoiDung(product.getNoiDung());
        dto.setNgayXuatBan(product.getNgayXuatBan());
        dto.setNgayCapNhat(product.getNgayCapNhat());
        dto.setDaXuatBan(product.getDaXuatBan());
        dto.setSoLuongTon(product.getSoLuongTon());
        dto.setGiaNhap(product.getGiaNhap());
        dto.setGiaBan(product.getGiaBan());
        dto.setPhanTramGiamGia(product.getPhanTramGiamGia());
        dto.setAnhBia(product.getAnhBia());
        dto.setReviewCount(product.getReviewCount());
        dto.setAverageRating(product.getAverageRating());
        dto.setLengthCm(product.getLengthCm());
        dto.setWidthCm(product.getWidthCm());
        dto.setHeightCm(product.getHeightCm());
        dto.setWeightKg(product.getWeightKg());
        return dto;
    }
} 