package com.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "global_whatsapp_template")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalWhatsAppTemplate {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String eventKey;

    @Column(nullable = false)
    private String templateName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String languageCode;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private Integer variableCount;

    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}