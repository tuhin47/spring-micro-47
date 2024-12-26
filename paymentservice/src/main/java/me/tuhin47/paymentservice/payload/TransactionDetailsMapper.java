package me.tuhin47.paymentservice.payload;

import me.tuhin47.payload.request.TransactionRequest;
import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.paymentservice.model.TransactionDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, imports = {Instant.class})
public interface TransactionDetailsMapper {
    PaymentResponse toDto(TransactionDetails transactionDetails);

    @Mapping(target = "paymentDate", expression = "java(Instant.now())")
    @Mapping(target = "paymentStatus", constant = "SUCCESS")
    TransactionDetails toEntity(TransactionRequest transactionRequest);

}