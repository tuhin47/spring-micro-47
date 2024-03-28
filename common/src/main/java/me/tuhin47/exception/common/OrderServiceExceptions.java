package me.tuhin47.exception.common;

import me.tuhin47.exception.EntityNotFoundException;

import java.util.function.Function;

public class OrderServiceExceptions {
    public static final Function<String, RuntimeException> ORDER_NOT_FOUND = id -> new EntityNotFoundException("Order", "id", id);

}
