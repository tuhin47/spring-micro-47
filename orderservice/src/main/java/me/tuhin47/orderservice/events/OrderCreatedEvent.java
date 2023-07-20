package me.tuhin47.orderservice.events;

import lombok.Data;
import me.tuhin47.saga.events.AbstractEvent;

@Data
public class OrderCreatedEvent extends AbstractEvent<String> {

    private long orderId;
    private long productId;
    private long userId;
//    private String addressId;
    private long quantity;
    private String orderStatus;
}
