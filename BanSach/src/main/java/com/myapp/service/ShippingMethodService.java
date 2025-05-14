package com.myapp.service;

import com.myapp.model.ShippingMethod;
import java.util.List;

public interface ShippingMethodService {
    List<ShippingMethod> findAllActive();
    ShippingMethod findById(Integer id);
} 