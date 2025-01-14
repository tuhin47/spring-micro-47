package me.tuhin47.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class ProductsPrice implements BaseResponse {
    private List<ProductResponse> productResponses;
    private Double price;
//    TODO : Add Descriptions for discounts
}
