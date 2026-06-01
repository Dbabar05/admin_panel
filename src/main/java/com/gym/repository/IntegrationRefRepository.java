package com.gym.repository;

import com.gym.entity.IntegrationRef;
import com.gym.enums.IntegrationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IntegrationRefRepository extends JpaRepository<IntegrationRef, UUID> {
    Optional<IntegrationRef> findByService(IntegrationType service);
}
