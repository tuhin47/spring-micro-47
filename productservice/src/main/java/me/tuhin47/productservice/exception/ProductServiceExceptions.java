package me.tuhin47.productservice.exception;

import me.tuhin47.exception.EntityNotFoundException;
import me.tuhin47.productservice.entity.Product;

import java.util.function.Function;

public class ProductServiceExceptions {
    public static final Function<String, RuntimeException> PRODUCT_NOT_FOUND = id -> new EntityNotFoundException(Product.class, "id", id);
}
