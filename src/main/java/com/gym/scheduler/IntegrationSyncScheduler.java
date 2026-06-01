package com.gym.scheduler;

import com.gym.service.IntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IntegrationSyncScheduler {

    private final IntegrationService integrationService;

    public IntegrationSyncScheduler(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    // Run every 5 minutes
    @Scheduled(fixedRate = 300000)
    public void syncIntegrations() {
        log.info("Starting scheduled integration sync...");
        try {
            integrationService.syncIntegrations();
            log.info("Integration sync completed successfully.");
        } catch (Exception e) {
            log.error("Error during scheduled integration sync: ", e);
        }
    }
}
