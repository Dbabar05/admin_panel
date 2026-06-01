package com.gym.service;

import com.gym.dto.whatsapp.CreateGlobalTemplateRequestDto;
import com.gym.dto.whatsapp.GlobalTemplateResponseDto;
import com.gym.entity.GlobalWhatsAppTemplate;
import com.gym.repository.GlobalWhatsAppTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GlobalWhatsAppTemplateService {

    private final GlobalWhatsAppTemplateRepository repository;

        public GlobalTemplateResponseDto createTemplate(CreateGlobalTemplateRequestDto request) {

        GlobalWhatsAppTemplate template = GlobalWhatsAppTemplate.builder()
                        .eventKey(request.getEventKey())
                        .templateName(request.getTemplateName())
                        .category(request.getCategory())
                        .languageCode(request.getLanguageCode())
                        .body(request.getBody())
                        .variableCount(request.getVariableCount())
                        .active(true)

                        .build();

        repository.save(template);

        return map(template);
    }

    public List<GlobalTemplateResponseDto> getTemplates() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public void updateStatus(UUID templateId, Boolean active) {

        GlobalWhatsAppTemplate template = repository.findById(templateId)
                        .orElseThrow(() -> new RuntimeException("Template not found."));

        template.setActive(active);

        repository.save(template);
    }

    private GlobalTemplateResponseDto map(GlobalWhatsAppTemplate template) {

        return GlobalTemplateResponseDto.builder()
                .id(template.getId())
                .eventKey(template.getEventKey())
                .templateName(template.getTemplateName())
                .category(template.getCategory())
                .languageCode(template.getLanguageCode())
                .body(template.getBody())
                .variableCount(template.getVariableCount())
                .active(template.getActive())
                .build();
    }
}
