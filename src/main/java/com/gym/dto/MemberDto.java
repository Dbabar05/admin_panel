package com.gym.dto;

import lombok.Data;

@Data
public class MemberDto {
    private java.util.UUID id;
    private String name;
    private String phone;
    private String status;
    private java.util.UUID tenantId;
}
