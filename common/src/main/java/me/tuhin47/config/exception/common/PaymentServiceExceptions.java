package me.tuhin47.config.exception.common;

import me.tuhin47.config.exception.apierror.EntityNotFoundException;

import java.util.function.Function;

public class PaymentServiceExceptions {
    public static final Function<String, RuntimeException> PAYMENT_NOT_FOUND_BY_ORDERID = id -> new EntityNotFoundException("Transactional Details", "order id", id);
}
