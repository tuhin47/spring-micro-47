package me.tuhin47.paymentservice.exception;

import me.tuhin47.exception.EntityNotFoundException;
import me.tuhin47.paymentservice.model.TransactionDetails;

import java.util.function.Function;

public class PaymentServiceExceptions {
    public static final Function<String, RuntimeException> PAYMENT_NOT_FOUND_BY_ORDERID = id -> new EntityNotFoundException(TransactionDetails.class, "order id", id);
}
