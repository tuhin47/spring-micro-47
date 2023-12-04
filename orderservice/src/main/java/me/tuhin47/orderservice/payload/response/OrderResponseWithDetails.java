package me.tuhin47.orderservice.payload.response;

import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.payload.response.ProductResponse;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link me.tuhin47.orderservice.model.Order}
 */
public record OrderResponseWithDetails(String id, long quantity, Instant orderDate, String orderStatus,
                                       @NumberFormat(pattern = "$###,###,###.00", style = NumberFormat.Style.CURRENCY) double amount,
                                       ProductResponse productResponse,
                                       PaymentResponse paymentResponse) implements Serializable {
}