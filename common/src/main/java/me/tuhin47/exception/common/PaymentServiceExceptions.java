package me.tuhin47.exception.common;

import me.tuhin47.exception.EntityNotFoundException;

import java.util.function.Function;

public class PaymentServiceExceptions {
    public static final Function<String, RuntimeException> PAYMENT_NOT_FOUND_BY_ORDERID = id -> new EntityNotFoundException("Transactional Details", "order id", id);
}
