package com.gym.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Long id;
    private Double amount;
    private String status;
    private LocalDateTime paymentDate;
    private String method;
    private Long tenantId;
}
