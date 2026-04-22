package com.gym.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TenantDto {
    private Long id;
    private String gymName;
    private String ownerName;
    private String email;
    private String status;
    private Long planId;
    private LocalDateTime createdAt;
}
