package me.tuhin47.productservice.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import me.tuhin47.productservice.domain.enums.ProductType;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DTO for {@link me.tuhin47.productservice.domain.entity.Product}
 */

public record ProductRequest(@NotBlank @Length(max = 50) String productName,
                             ProductType productType,
                             @PositiveOrZero double price,
                             @PositiveOrZero long quantity) implements Serializable {
}