package me.tuhin47.productservice.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    ELECTRONIC("electronic"),
    FOOD("Food"),
    DRESS("Dress"),
    FURNITURE("Furniture");

    private final String type;
}
