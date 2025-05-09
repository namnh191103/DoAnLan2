package com.myapp.repository;

import com.myapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByDuongDan(String duongDan);
    Optional<Product> findByTuaDe(String tuaDe);
    Page<Product> findByTuaDeContainingOrTomTatContaining(String tuaDe, String tomTat, Pageable pageable);
} 