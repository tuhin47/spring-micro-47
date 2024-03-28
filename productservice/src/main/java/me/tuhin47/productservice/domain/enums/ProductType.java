package me.tuhin47.productservice.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    ELECTRONIC("Electronic",1),
    FOOD("Food",0),
    DRESS("Dress",3),
    FURNITURE("Furniture",4);

    private final String type;
    private final double discount;
}
