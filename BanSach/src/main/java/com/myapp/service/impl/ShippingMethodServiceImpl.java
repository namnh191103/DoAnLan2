package com.myapp.service.impl;

import com.myapp.model.ShippingMethod;
import com.myapp.repository.ShippingMethodRepository;
import com.myapp.service.ShippingMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingMethodServiceImpl implements ShippingMethodService {
    private final ShippingMethodRepository shippingMethodRepository;

    @Override
    public List<ShippingMethod> findAllActive() {
        return shippingMethodRepository.findByActiveTrue();
    }

    @Override
    public ShippingMethod findById(Integer id) {
        return shippingMethodRepository.findById(id).orElse(null);
    }
} 