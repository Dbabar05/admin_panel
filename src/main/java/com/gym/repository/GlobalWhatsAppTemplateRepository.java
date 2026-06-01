package com.gym.repository;

import com.gym.entity.GlobalWhatsAppTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GlobalWhatsAppTemplateRepository extends JpaRepository<GlobalWhatsAppTemplate, UUID> {

    List<GlobalWhatsAppTemplate> findByActiveTrue();
}
