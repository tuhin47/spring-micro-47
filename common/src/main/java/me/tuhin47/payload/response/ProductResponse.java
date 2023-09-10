package me.tuhin47.payload.response;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link me.tuhin47.productservice.domain.entity.Product}
 */
@Value
@Builder
public class ProductResponse implements Serializable {

    String id;
    String productName;
    double price;
    long quantity;
}