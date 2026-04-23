package com.gym.service;

import com.gym.communication.dispatcher.EventDispatcher;
import com.gym.communication.event.TenantCreatedEvent;
import com.gym.dto.ResponseDto;
import com.gym.dto.TenantDto;
import com.gym.entity.Tenant;
import com.gym.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;
    private final EventDispatcher eventDispatcher;

    @Transactional
    public ResponseDto createTenant(TenantDto tenantDto) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(tenantDto, tenant);
        tenant.setStatus("active");
        Tenant savedTenant = tenantRepository.save(tenant);

        // Dispatch Event
        TenantCreatedEvent event = new TenantCreatedEvent(
                savedTenant.getId(),
                savedTenant.getEmail(),
                savedTenant.getOwnerName()
        );
        eventDispatcher.dispatch(event);

        return ResponseDto.builder().code(201).message("Tenant Added Successfully").build();
    }

    public List<TenantDto> getAllTenants() {
        return tenantRepository.findAll().stream().map(t -> {
            TenantDto dto = new TenantDto();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    public TenantDto getTenantById(UUID id) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow();
        TenantDto dto = new TenantDto();
        BeanUtils.copyProperties(tenant, dto);
        return dto;
    }

    public TenantDto updateTenantStatus(UUID id, String status) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow();
        tenant.setStatus(status);
        Tenant saved = tenantRepository.save(tenant);
        TenantDto dto = new TenantDto();
        BeanUtils.copyProperties(saved, dto);
        return dto;
    }

    public ResponseDto deleteTenant(UUID id) {

        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        tenantRepository.delete(tenant);

        return ResponseDto.builder().code(201).message("Tenant Deleted Successfully").build();

    }

    public ResponseDto updateTenant(UUID id, TenantDto request) {

        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        tenant.setGymName(request.getGymName());
        tenant.setOwnerName(request.getOwnerName());
        tenant.setEmail(request.getEmail());
        tenant.setPhoneNumber(request.getPhoneNumber());
        tenant.setPlanId(request.getPlanId());

        tenantRepository.save(tenant);
        return ResponseDto.builder().code(201).message("Tenant Updated Successfully").build();
    }


    public ResponseDto activateTenant(UUID id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        tenant.setStatus("active");
        tenantRepository.save(tenant);
        return ResponseDto.builder().code(201).message("Tenant Activated Successfully").build();

    }

    public ResponseDto deactivateTenant(UUID id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        tenant.setStatus("In-active");
        tenantRepository.save(tenant);
        return ResponseDto.builder().code(201).message("Tenant De-Activated Successfully").build();

    }
}
