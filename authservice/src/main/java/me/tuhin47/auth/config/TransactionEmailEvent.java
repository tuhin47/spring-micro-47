package me.tuhin47.auth.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class TransactionEmailEvent extends ApplicationEvent {

    private final String title;
    private final String body;
    private final String toAddress;

    public TransactionEmailEvent(Object source, String toAddress, String title, String body) {
        super(source);
        this.toAddress = toAddress;
        this.title = title;
        this.body = body;
    }
}