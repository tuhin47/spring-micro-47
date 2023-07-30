package me.tuhin47.productservice.payload.request;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * DTO for {@link me.tuhin47.productservice.entity.Product}
 */
@Value
public class ProductRequest implements Serializable {
    @NotBlank
    @Length(max = 50)
    String productName;

    @PositiveOrZero
    double price;

    @PositiveOrZero
    long quantity;
}