package com.gym.repository;

import com.gym.entity.GymRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GymRoleRepository extends JpaRepository<GymRole, UUID> {
    Optional<GymRole> findByRoleCode(String name);
}
