package me.tuhin47.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
public class TopOrderDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1704759362817709990L;
    private String buyerId;
    private long quantity;
    private double amount;
    @Setter
    private UserResponse userResponse;


    public TopOrderDto(String buyerId, long quantity, double amount) {
        this.buyerId = buyerId;
        this.quantity = quantity;
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyerId, quantity, amount);
    }

    @Override
    public String toString() {
        return "TopOrderDto[" +
            "buyerId=" + buyerId + ", " +
            "quantity=" + quantity + ", " +
            "amount=" + amount + ']';
    }


}