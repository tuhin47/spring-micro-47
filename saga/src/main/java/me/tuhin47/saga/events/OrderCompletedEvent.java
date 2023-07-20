package me.tuhin47.saga.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
public class OrderCompletedEvent extends AbstractEvent<String>{
    private String orderId;
    private String orderStatus;
}
