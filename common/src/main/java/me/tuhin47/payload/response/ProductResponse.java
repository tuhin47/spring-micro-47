package me.tuhin47.payload.response;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link me.tuhin47.productservice.domain.entity.Product}
 */
@Data
@Builder
public class ProductResponse implements Serializable {

    String id;
    String productName;
    double price;
    double discount;
    long quantity;
    String productType;
}