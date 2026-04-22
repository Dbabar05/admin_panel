package com.gym.service;

import com.gym.dto.TenantDto;
import com.gym.entity.Tenant;
import com.gym.repository.TenantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public TenantDto createTenant(TenantDto tenantDto) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(tenantDto, tenant);
        tenant.setStatus("active");
        Tenant saved = tenantRepository.save(tenant);
        TenantDto response = new TenantDto();
        BeanUtils.copyProperties(saved, response);
        return response;
    }

    public List<TenantDto> getAllTenants() {
        return tenantRepository.findAll().stream().map(t -> {
            TenantDto dto = new TenantDto();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    public TenantDto getTenantById(Long id) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow();
        TenantDto dto = new TenantDto();
        BeanUtils.copyProperties(tenant, dto);
        return dto;
    }

    public TenantDto updateTenantStatus(Long id, String status) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow();
        tenant.setStatus(status);
        Tenant saved = tenantRepository.save(tenant);
        TenantDto dto = new TenantDto();
        BeanUtils.copyProperties(saved, dto);
        return dto;
    }
}
