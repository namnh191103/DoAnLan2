package com.myapp.service;

import com.myapp.dto.ProductDTO;
import com.myapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO);
    ProductDTO findById(Integer id);
    Product findEntityById(Integer id);
    ProductDTO findByDuongDan(String duongDan);
    ProductDTO findByTuaDe(String tuaDe);
    List<ProductDTO> findAll();
    Page<ProductDTO> findAll(Pageable pageable);
    Page<ProductDTO> search(String keyword, Pageable pageable);
    ProductDTO update(Integer id, ProductDTO productDTO);
    void delete(Integer id);
} 