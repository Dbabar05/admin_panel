package com.gym.dto;

import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String name;
    private String phone;
    private String status;
    private Long tenantId;
}
