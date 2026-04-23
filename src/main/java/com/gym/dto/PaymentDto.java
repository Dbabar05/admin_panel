package com.gym.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private java.util.UUID id;
    private Double amount;
    private String status;
    private LocalDateTime paymentDate;
    private String method;
    private java.util.UUID tenantId;
}
