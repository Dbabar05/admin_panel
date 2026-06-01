package com.gym.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDto {

    private UUID id;
    private String name;
    private Double price;

    // 🔥 Changed from String → List
    private List<PlanFeatureDto> features;
}