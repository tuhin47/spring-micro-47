package me.tuhin47.orderservice.payload.mapper;

import me.tuhin47.orderservice.model.Order;
import me.tuhin47.orderservice.payload.response.OrderResponseWithDetails;
import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.payload.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {


    @Mapping(source = "order.id", target = "id")
    @Mapping(source = "order.quantity", target = "quantity")
    @Mapping(source = "order.amount", target = "amount")
    OrderResponseWithDetails toDtoWithDetails(Order order, ProductResponse productResponse, PaymentResponse paymentResponse);
}