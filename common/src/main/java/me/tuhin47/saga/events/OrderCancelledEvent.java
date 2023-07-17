package me.tuhin47.saga.events;

import lombok.Data;

@Data
public class OrderCancelledEvent {
    private String orderId;
    private String orderStatus;
}
