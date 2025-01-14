package me.tuhin47.payload.response;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for {@link me.tuhin47.productservice.domain.entity.Product}
 */
@Data
@Builder
public class ProductResponse implements BaseResponse {

    String id;
    String productName;
    double price;
    double discount;
    long quantity;
    String productType;
}