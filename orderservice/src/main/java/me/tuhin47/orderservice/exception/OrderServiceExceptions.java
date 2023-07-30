package me.tuhin47.orderservice.exception;

import me.tuhin47.exception.EntityNotFoundException;
import me.tuhin47.orderservice.model.Order;

import java.util.function.Function;

public class OrderServiceExceptions {
    public static final Function<String, RuntimeException> ORDER_NOT_FOUND = id -> new EntityNotFoundException(Order.class, "id", id);
}
