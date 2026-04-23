package com.gym.communication.event;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class TenantCreatedEvent extends BaseEvent {
    private UUID tenantId;
    private String email;
    private String ownerName;

    public TenantCreatedEvent(UUID tenantId, String email, String ownerName) {
        super("TENANT_CREATED", tenantId);
        this.tenantId = tenantId;
        this.email = email;
        this.ownerName = ownerName;
    }
}
