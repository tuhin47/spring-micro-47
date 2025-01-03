package me.tuhin47.productservice.rules;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import me.tuhin47.client.OrderService;
import me.tuhin47.config.RequestDataHolder;
import me.tuhin47.config.redis.RedisUserService;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.payload.response.ProductResponse;
import me.tuhin47.payload.response.ProductsPrice;
import me.tuhin47.payload.response.TopOrderDto;
import me.tuhin47.utils.InstantUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TopBuyerRule implements ProductDiscount {

    public static final double YEARLY_TOP10_USER_DISCOUNT = 0.1;
    public static final double MONTH_TOP10_USER_DISCOUNT = 0.08;


    private final OrderService orderService;

    @Resource(name = "requestScopedBean")
    private RequestDataHolder requestDataHolder;

    @Override
    public boolean evaluate(ProductsPrice productsPrice) {

        UserRedis currentUser = RedisUserService.getCurrentUser();
        if (currentUser == null || currentUser.getUserId() == null) {
            return false;
        }

        List<TopOrderDto> top10OrderYearly = orderService.getTop10OrderByDateRange(InstantUtil.startOfYear(), InstantUtil.endOfCurrentYear()).getBody();
        boolean yearlyMatch = currenUserMatch(top10OrderYearly, currentUser);

        if (yearlyMatch) {
            requestDataHolder.setRequestDataYearly(top10OrderYearly);
            return true;
        }

        List<TopOrderDto> top10OrderMonthly = orderService.getTop10OrderByDateRange(InstantUtil.startOfMonth(), InstantUtil.endOfCurrentMonth()).getBody();
        boolean monthMatch = currenUserMatch(top10OrderMonthly, currentUser);

        if (monthMatch) {
            requestDataHolder.setRequestDataMonthly(top10OrderMonthly);
            return true;
        }

        return false;
    }

    private static boolean currenUserMatch(List<TopOrderDto> list, UserRedis currentUser) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        return list.stream().anyMatch(topOrderDto -> topOrderDto.getBuyerId().equals(currentUser.getUserId()));
    }

    @Override
    public void execute(ProductsPrice productsPrice) {

        double discountRate = requestDataHolder.getRequestDataYearly() != null ? YEARLY_TOP10_USER_DISCOUNT : MONTH_TOP10_USER_DISCOUNT;

        double totalDiscount = productsPrice.getProductResponses().stream().mapToDouble(
            response -> RuleUtils.updateDiscountPrice(response, (ProductResponse productResponse) -> discountRate)
        ).sum();

        productsPrice.setPrice(productsPrice.getPrice() - totalDiscount);
    }

    @Override
    public Integer getSortOrder() {
        return 0;
    }

}
