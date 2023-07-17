package me.tuhin47.core.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDetails {
    private String name;
    private String cardNumber;
    private Integer validUntilMonth;
    private Integer validUntilYear;
    private Integer cvv;
}
