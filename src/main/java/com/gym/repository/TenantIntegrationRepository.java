package com.gym.repository;

import com.gym.entity.TenantIntegration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantIntegrationRepository extends JpaRepository<TenantIntegration, java.util.UUID> {
}
