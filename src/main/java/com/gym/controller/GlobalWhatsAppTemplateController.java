package com.gym.controller;

import com.gym.dto.whatsapp.CreateGlobalTemplateRequestDto;
import com.gym.dto.whatsapp.GlobalTemplateResponseDto;
import com.gym.service.GlobalWhatsAppTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/whatsapp/global-templates")
@RequiredArgsConstructor
public class GlobalWhatsAppTemplateController {

    private final GlobalWhatsAppTemplateService service;

    @PostMapping
    public ResponseEntity<GlobalTemplateResponseDto> createTemplate(@RequestBody CreateGlobalTemplateRequestDto request) {

        return ResponseEntity.ok(service.createTemplate(request));
    }

    @GetMapping
    public ResponseEntity<List<GlobalTemplateResponseDto>> getTemplates() {

        return ResponseEntity.ok(service.getTemplates());
    }

    @PatchMapping("/{templateId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID templateId, @RequestParam Boolean active) {

        service.updateStatus(templateId, active);

        return ResponseEntity.ok("Template status updated.");
    }
}
