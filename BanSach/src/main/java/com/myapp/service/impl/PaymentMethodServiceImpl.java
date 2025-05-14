package com.myapp.service.impl;

import com.myapp.model.PaymentMethod;
import com.myapp.repository.PaymentMethodRepository;
import com.myapp.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> findAllActive() {
        return paymentMethodRepository.findByActiveTrue();
    }

    @Override
    public PaymentMethod findById(Integer id) {
        return paymentMethodRepository.findById(id).orElse(null);
    }
} 