package com.gym.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubscriptionDto {
    private Long id;
    private Long tenantId;
    private Long planId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
