package me.tuhin47.productservice.rules;

import me.tuhin47.payload.response.ProductsPrice;
import me.tuhin47.rule.Rule;

public interface ProductDiscount extends Rule<ProductsPrice> {

    Integer getSortOrder();
}
