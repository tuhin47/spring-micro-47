package me.tuhin47.paymentservice.events;

import lombok.RequiredArgsConstructor;
import me.tuhin47.paymentservice.model.TransactionDetails;
import me.tuhin47.paymentservice.repository.TransactionDetailsRepository;
import me.tuhin47.saga.events.PaymentCancelledEvent;
import me.tuhin47.saga.events.PaymentProcessedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class PaymentsEventHandler {

    private final TransactionDetailsRepository paymentRepository;

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        TransactionDetails payment = TransactionDetails.builder()
                .id(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentMode("CARD")
                .amount(1000)
                .paymentDate(Instant.now())
                .paymentStatus("COMPLETED")
                .build();

        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelledEvent event) {
        TransactionDetails payment = paymentRepository.findById(event.getPaymentId()).get();
        payment.setPaymentStatus(event.getPaymentStatus());
        paymentRepository.save(payment);
    }
}
