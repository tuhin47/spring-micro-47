package me.tuhin47.orderservice.command;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class CreateOrderCommand {

    private String orderId;
    private String productId;
    private String userId;
//    private String addressId;
    private long quantity;
    private String orderStatus;
}
