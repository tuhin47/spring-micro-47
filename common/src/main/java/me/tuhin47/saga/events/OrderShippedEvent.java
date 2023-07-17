package me.tuhin47.saga.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderShippedEvent {
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;
}
