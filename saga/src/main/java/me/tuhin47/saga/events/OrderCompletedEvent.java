package me.tuhin47.saga.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCompletedEvent {
    private long orderId;
    private String orderStatus;
}
