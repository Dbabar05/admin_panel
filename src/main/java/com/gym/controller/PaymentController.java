package com.gym.controller;

import com.gym.dto.PaymentDto;
import com.gym.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDto>> getPayments(Pageable pageable) {
        return ResponseEntity.ok(paymentService.getPayments(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable java.util.UUID id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }
}
