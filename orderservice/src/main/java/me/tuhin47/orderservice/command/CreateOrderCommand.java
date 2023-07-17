package me.tuhin47.orderservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private Long orderId;
    private Long productId;
    private Long userId;
//    private String addressId;
    private long quantity;
    private String orderStatus;
}
