package me.tuhin47.productservice.rules;

import lombok.RequiredArgsConstructor;
import me.tuhin47.payload.response.ProductsPrice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountRuleEngine {

    private final List<ProductDiscount> rules;

    public void applyRules(ProductsPrice productsPrice) {
        for (ProductDiscount rule : rules) {
            if (rule.evaluate(productsPrice)) {
                rule.execute(productsPrice);
            }
        }
    }


}
