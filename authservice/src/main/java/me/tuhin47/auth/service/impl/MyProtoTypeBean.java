package me.tuhin47.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(value = SCOPE_PROTOTYPE)
@Slf4j
public class MyProtoTypeBean {
    Instant instant;

    public MyProtoTypeBean() {
        this.instant = Instant.now();
    }

    public void showSpentTime() {
        log.info("spent time : {}", Duration.between(Instant.now(), instant));
    }
}
