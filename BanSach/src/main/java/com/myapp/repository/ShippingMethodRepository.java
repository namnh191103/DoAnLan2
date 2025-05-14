package com.myapp.repository;

import com.myapp.model.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Integer> {
    List<ShippingMethod> findByActiveTrue();
} 