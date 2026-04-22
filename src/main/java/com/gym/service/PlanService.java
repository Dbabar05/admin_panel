package com.gym.service;

import com.gym.dto.PlanDto;
import com.gym.entity.Plan;
import com.gym.repository.PlanRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<PlanDto> getAllPlans() {
        return planRepository.findAll().stream().map(p -> {
            PlanDto dto = new PlanDto();
            BeanUtils.copyProperties(p, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    public PlanDto createPlan(PlanDto planDto) {
        Plan plan = new Plan();
        BeanUtils.copyProperties(planDto, plan);
        Plan saved = planRepository.save(plan);
        PlanDto response = new PlanDto();
        BeanUtils.copyProperties(saved, response);
        return response;
    }

    public PlanDto updatePlan(Long id, PlanDto planDto) {
        Plan plan = planRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(planDto, plan, "id");
        Plan saved = planRepository.save(plan);
        PlanDto response = new PlanDto();
        BeanUtils.copyProperties(saved, response);
        return response;
    }
}
