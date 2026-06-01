package com.gym.dto.whatsapp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppTemplateResponseDto {

    private UUID id;
    private String metaTemplateId;
    private String templateName;
    private String languageCode;
    private String category;
    private String status;
    private Boolean active;
}
