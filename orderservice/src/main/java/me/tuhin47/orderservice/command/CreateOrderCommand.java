package me.tuhin47.orderservice.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import me.tuhin47.saga.commands.AbstractCommand;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
public class CreateOrderCommand extends AbstractCommand<String> {

    private long orderId;
    private long productId;
    private long userId;
//    private String addressId;
    private long quantity;
    private String orderStatus;
}
