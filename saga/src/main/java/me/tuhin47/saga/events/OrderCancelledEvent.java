package me.tuhin47.saga.events;

import lombok.Data;

@Data
public class OrderCancelledEvent {
    private long orderId;
    private String orderStatus;
}
