package me.tuhin47.config.exception.common;

import me.tuhin47.config.exception.apierror.CustomException;
import me.tuhin47.config.exception.apierror.EntityNotFoundException;
import org.springframework.http.HttpStatus;

import java.util.function.Function;
import java.util.function.Supplier;

public class ProductServiceExceptions {

    public static final Function<String, RuntimeException> PRODUCT_NOT_FOUND = id -> new EntityNotFoundException("Product", "id", id);
    public static final Supplier<RuntimeException> INSUFFICIENT_QUANTITY = () -> new CustomException("Product does not have sufficient Quantity", "INSUFFICIENT_QUANTITY", HttpStatus.FAILED_DEPENDENCY.value());
}
