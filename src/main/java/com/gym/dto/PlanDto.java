package com.gym.dto;

import lombok.Data;

@Data
public class PlanDto {
    private Long id;
    private String name;
    private Double price;
    private String features;
}
