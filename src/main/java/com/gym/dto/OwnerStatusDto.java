package com.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerStatusDto {
    private UUID tenantId;
    private String ownerStatus;  // CREATING / SUCCESS / FAILED
    private String ownerEmail;
    private String password;
}
