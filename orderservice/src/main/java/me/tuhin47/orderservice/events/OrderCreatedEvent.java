package me.tuhin47.orderservice.events;

import lombok.Data;

@Data
public class OrderCreatedEvent {

    private long orderId;
    private long productId;
    private long userId;
//    private String addressId;
    private Integer quantity;
    private String orderStatus;
}
