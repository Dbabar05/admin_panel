package com.gym.dto.whatsapp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGlobalTemplateRequestDto {

    private String eventKey;

    private String templateName;

    private String category;

    private String languageCode;

    private String body;

    private Integer variableCount;
}
