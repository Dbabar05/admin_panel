package com.gym.communication.dispatcher;

import com.gym.communication.event.BaseEvent;
import com.gym.communication.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventDispatcher {

    private final List<EventHandler<? extends BaseEvent>> handlers;

    @SuppressWarnings("unchecked")
    public void dispatch(BaseEvent event) {
        log.info("Dispatching event: {} for entity: {}", event.getEventType(), event.getEntityId());
        
        handlers.stream()
                .filter(handler -> handler.supports(event))
                .forEach(handler -> {
                    log.debug("Found matching handler: {}", handler.getClass().getSimpleName());
                    ((EventHandler<BaseEvent>) handler).handle(event);
                });
    }
}
