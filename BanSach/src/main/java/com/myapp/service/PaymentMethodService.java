package com.myapp.service;

import com.myapp.model.PaymentMethod;
import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> findAllActive();
    PaymentMethod findById(Integer id);
} 