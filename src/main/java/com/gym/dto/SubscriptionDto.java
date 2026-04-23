package com.gym.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubscriptionDto {
    private java.util.UUID id;
    private java.util.UUID tenantId;
    private java.util.UUID planId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
