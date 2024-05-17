package me.tuhin47.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.auth.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEmailEventListener {

    private final EmailService emailService;

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleTransactionEvent(TransactionEmailEvent event) {
        log.info("handleTransactionEvent() called with: event = [{}]", event);
        emailService.sendSimpleMessage(event.getToAddress(), event.getTitle(), event.getBody());
    }
}