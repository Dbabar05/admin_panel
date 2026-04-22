package com.gym.service;

import com.gym.dto.IntegrationDto;
import com.gym.dto.TenantIntegrationDto;
import com.gym.entity.Integration;
import com.gym.entity.TenantIntegration;
import com.gym.repository.IntegrationRepository;
import com.gym.repository.TenantIntegrationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegrationService {
    private final IntegrationRepository integrationRepository;
    private final TenantIntegrationRepository tenantIntegrationRepository;

    public IntegrationService(IntegrationRepository integrationRepository,
                              TenantIntegrationRepository tenantIntegrationRepository) {
        this.integrationRepository = integrationRepository;
        this.tenantIntegrationRepository = tenantIntegrationRepository;
    }

    public List<IntegrationDto> getAllIntegrations() {
        return integrationRepository.findAll().stream().map(i -> {
            IntegrationDto dto = new IntegrationDto();
            BeanUtils.copyProperties(i, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    public IntegrationDto createIntegration(IntegrationDto dto) {
        Integration integration = new Integration();
        BeanUtils.copyProperties(dto, integration);
        Integration saved = integrationRepository.save(integration);
        IntegrationDto response = new IntegrationDto();
        BeanUtils.copyProperties(saved, response);
        return response;
    }

    public IntegrationDto toggleIntegration(Long id) {
        Integration i = integrationRepository.findById(id).orElseThrow();
        i.setIsActive(!i.getIsActive());
        Integration saved = integrationRepository.save(i);
        IntegrationDto response = new IntegrationDto();
        BeanUtils.copyProperties(saved, response);
        return response;
    }

    public TenantIntegrationDto enableForTenant(TenantIntegrationDto dto) {
        TenantIntegration ti = new TenantIntegration();
        BeanUtils.copyProperties(dto, ti);
        TenantIntegration saved = tenantIntegrationRepository.save(ti);
        TenantIntegrationDto response = new TenantIntegrationDto();
        BeanUtils.copyProperties(saved, response);
        return response;
    }
}
