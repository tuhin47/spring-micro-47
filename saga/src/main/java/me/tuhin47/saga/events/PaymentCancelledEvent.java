package me.tuhin47.saga.events;

import lombok.Data;

@Data
public class PaymentCancelledEvent {
    private long paymentId;
    private long orderId;
    private String paymentStatus;
}
