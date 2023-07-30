package me.tuhin47.orderservice.payload.response;

import lombok.Value;
import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.payload.response.ProductResponse;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link me.tuhin47.orderservice.model.Order}
 */
@Value
public class OrderResponseWithDetails implements Serializable {
    String id;
    long quantity;
    Instant orderDate;
    String orderStatus;
    double amount;
    ProductResponse productResponse;
    PaymentResponse paymentResponse;
}