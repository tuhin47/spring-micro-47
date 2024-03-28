package me.tuhin47.productservice.rules;

import me.tuhin47.payload.response.ProductResponse;
import me.tuhin47.payload.response.ProductsPrice;
import me.tuhin47.productservice.domain.enums.ProductType;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductTypeRule implements ProductDiscount {

    public static final Function<ProductResponse, Double> PRODUCT_TYPE_DISCOUNT = (ProductResponse productResponse) -> {
        ProductType productType = ProductType.valueOf(productResponse.getProductType());
        return productType.getDiscount() / 100;
    };

    @Override
    public boolean evaluate(ProductsPrice productsPrice) {
        return productsPrice.getPrice() >= 1000;
    }

    @Override
    public void execute(ProductsPrice productsPrice) {

        double totalTypeDiscount = productsPrice.getProductResponses().stream()
                                                .mapToDouble(productResponse -> RuleUtils.updateDiscountPrice(productResponse, PRODUCT_TYPE_DISCOUNT)).sum();

        productsPrice.setPrice(productsPrice.getPrice() - totalTypeDiscount);
    }

    @Override
    public Integer getSortOrder() {
        return 3;
    }
}
