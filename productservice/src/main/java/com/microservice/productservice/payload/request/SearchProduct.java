package com.microservice.productservice.payload.request;

/**
 * Projection for {@link com.microservice.productservice.entity.Product}
 */
public interface SearchProduct {
    String getProductName();
    long getPrice();
}