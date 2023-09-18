package me.tuhin47.productservice.payload.request;

import me.tuhin47.productservice.domain.enums.ProductType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * DTO for {@link me.tuhin47.productservice.domain.entity.Product}
 */

public record ProductRequest(@NotBlank @Length(max = 50) String productName,
                             ProductType productType,
                             @PositiveOrZero double price,
                             @PositiveOrZero long quantity) implements Serializable {
}