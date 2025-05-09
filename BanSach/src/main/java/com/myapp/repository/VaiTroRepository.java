package com.myapp.repository;

import com.myapp.model.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> {
    VaiTro findByTenVaiTro(String tenVaiTro);
} 