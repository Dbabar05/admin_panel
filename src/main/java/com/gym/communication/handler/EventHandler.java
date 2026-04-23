package com.gym.communication.handler;

import com.gym.communication.event.BaseEvent;

public interface EventHandler<T extends BaseEvent> {
    boolean supports(BaseEvent event);
    void handle(T event);
}
