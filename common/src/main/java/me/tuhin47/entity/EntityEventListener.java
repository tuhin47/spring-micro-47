package me.tuhin47.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class EntityEventListener {

    private final EntitySuccessHandler[] entitySuccessHandlers;

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEntitySuccessEvent(EntitySuccessEvent event) {
        for (EntitySuccessHandler entitySuccessHandler : entitySuccessHandlers) {
            entitySuccessHandler.executeTask(event);
        }
    }

}