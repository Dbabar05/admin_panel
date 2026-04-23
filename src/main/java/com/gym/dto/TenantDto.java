package com.gym.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TenantDto {
    private UUID id;
    private String gymName;
    private String ownerName;
    private String phoneNumber;
    private String address;
    private String email;
    private String status;
    private UUID planId;
    private LocalDateTime createdAt;
}
