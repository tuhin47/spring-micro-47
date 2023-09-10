package me.tuhin47.productservice.payload.response;

import me.tuhin47.productservice.domain.enums.ProductType;

/**
 * Projection for {@link me.tuhin47.productservice.domain.entity.Product}
 */
public interface ProductTypeCountReport {
    ProductType getProductType();

    long getCount();
}