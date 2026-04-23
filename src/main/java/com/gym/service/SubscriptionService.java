package com.gym.service;

import com.gym.dto.SubscriptionDto;
import com.gym.entity.Subscription;
import com.gym.repository.SubscriptionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public SubscriptionDto assignPlan(SubscriptionDto dto) {
        Subscription sub = new Subscription();
        BeanUtils.copyProperties(dto, sub);
        sub.setStartDate(LocalDateTime.now());
        sub.setStatus("active");
        Subscription saved = subscriptionRepository.save(sub);
        SubscriptionDto response = new SubscriptionDto();
        BeanUtils.copyProperties(saved, response);
        return response;
    }

    public SubscriptionDto updateSubscription(java.util.UUID id, String status) {
        Subscription sub = subscriptionRepository.findById(id).orElseThrow();
        sub.setStatus(status);
        Subscription saved = subscriptionRepository.save(sub);
        SubscriptionDto response = new SubscriptionDto();
        BeanUtils.copyProperties(saved, response);
        return response;
    }
}
