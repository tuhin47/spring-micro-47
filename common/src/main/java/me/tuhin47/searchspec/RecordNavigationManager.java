package me.tuhin47.searchspec;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class RecordNavigationManager {

    public static Pageable getPageable(HttpServletRequest request) {
        var all = Boolean.TRUE.toString().equals(request.getParameter("all"));
        int size = all ? Integer.MAX_VALUE : getValueFromRequest(request, "size");
        int page = all ? 0 : getValueFromRequest(request, "page");
        List<Sort.Order> orders = getSortOrders(request);
        return PageRequest.of(page, size, Sort.by(orders));
    }

    public static List<Sort.Order> getSortOrders(HttpServletRequest request) {
        String[] sort = {"id", "desc"};
        String sorts = request.getParameter("sort");

        if (sorts != null) {
            sort = sorts.split(";");
        }
        List<Sort.Order> orders = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return orders;
    }

    public static int getValueFromRequest(HttpServletRequest request, String param) {
        String paramValue = request.getParameter(param);
        if (paramValue == null) {
            switch (param) {
                case "size":
                    return 10;
                case "page":
                    return 0;
            }
        }
        return Integer.parseInt(paramValue);
    }

    public static Sort.Direction getSortDirection(String direction) {
        return Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.ASC);
    }
}