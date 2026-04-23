package com.gym.controller;

import com.gym.dto.SubscriptionDto;
import com.gym.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> assignPlan(@RequestBody SubscriptionDto dto) {
        return ResponseEntity.ok(subscriptionService.assignPlan(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SubscriptionDto> updateStatus(@PathVariable java.util.UUID id, @RequestParam String status) {
        return ResponseEntity.ok(subscriptionService.updateSubscription(id, status));
    }
}
