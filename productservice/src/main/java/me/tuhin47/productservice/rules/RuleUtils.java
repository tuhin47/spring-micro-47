package me.tuhin47.productservice.rules;

import me.tuhin47.payload.response.ProductResponse;

import java.util.function.Function;

public class RuleUtils {
    public static double updateDiscountPrice(ProductResponse productResponse, Function<ProductResponse, Double> discountRateSupplier) {
        double discount = productResponse.getPrice() * discountRateSupplier.apply(productResponse);
        productResponse.setDiscount(productResponse.getDiscount() + discount);
        return discount;
    }
}
