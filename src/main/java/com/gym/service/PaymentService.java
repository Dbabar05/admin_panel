package com.gym.service;

import com.gym.dto.PaymentDto;
import com.gym.entity.Payment;
import com.gym.repository.PaymentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Page<PaymentDto> getPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(p -> {
            PaymentDto dto = new PaymentDto();
            BeanUtils.copyProperties(p, dto);
            return dto;
        });
    }

    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        PaymentDto dto = new PaymentDto();
        BeanUtils.copyProperties(payment, dto);
        return dto;
    }
}
