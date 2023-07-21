package me.tuhin47.orderservice.events;

import lombok.Data;

@Data
public class OrderCreatedEvent {

    private String orderId;
    private String productId;
    private String userId;
//    private String addressId;
    private long quantity;
    private String orderStatus;
}
