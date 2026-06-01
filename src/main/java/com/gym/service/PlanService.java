package com.gym.service;

import com.gym.dto.PlanDto;
import com.gym.dto.PlanFeatureDto;
import com.gym.entity.Plan;
import com.gym.entity.PlanFeature;
import com.gym.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final PlanRepository planRepository;

    // 🔥 GET ALL
    public List<PlanDto> getAllPlans() {
        return planRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // 🔥 CREATE
    public PlanDto createPlan(PlanDto planDto) {

        Plan plan = new Plan();
        plan.setName(planDto.getName());
        plan.setPrice(planDto.getPrice());

        List<PlanFeature> features = planDto.getFeatures()
                .stream()
                .map(f -> {
                    PlanFeature pf = new PlanFeature();
                    pf.setFeature(f.getFeature());
                    pf.setPlan(plan); // 🔥 important
                    return pf;
                })
                .collect(Collectors.toList());

        plan.setFeatures(features);

        Plan saved = planRepository.save(plan);

        return mapToDto(saved);
    }

    // 🔥 UPDATE
    public PlanDto updatePlan(UUID id, PlanDto planDto) {

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        plan.setName(planDto.getName());
        plan.setPrice(planDto.getPrice());

        // 🔥 Clear old features (important for update)
        plan.getFeatures().clear();

        List<PlanFeature> updatedFeatures = planDto.getFeatures()
                .stream()
                .map(f -> {
                    PlanFeature pf = new PlanFeature();
                    pf.setFeature(f.getFeature());
                    pf.setPlan(plan);
                    return pf;
                })
                .collect(Collectors.toList());

        plan.getFeatures().addAll(updatedFeatures);

        Plan saved = planRepository.save(plan);

        return mapToDto(saved);
    }

    // 🔥 MAPPER
    private PlanDto mapToDto(Plan plan) {

        List<PlanFeatureDto> features = plan.getFeatures()
                .stream()
                .map(f -> PlanFeatureDto.builder()
                        .id(f.getId())
                        .feature(f.getFeature())
                        .build())
                .collect(Collectors.toList());

        return PlanDto.builder()
                .id(plan.getId())
                .name(plan.getName())
                .price(plan.getPrice())
                .features(features)
                .build();
    }
}