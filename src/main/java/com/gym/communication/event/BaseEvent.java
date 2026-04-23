package com.gym.communication.event;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseEvent {
    private String eventType;
    private UUID entityId;
    private LocalDateTime timestamp = LocalDateTime.now();

    protected BaseEvent(String eventType, UUID entityId) {
        this.eventType = eventType;
        this.entityId = entityId;
    }
}
