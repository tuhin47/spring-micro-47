package me.tuhin47.auth.config;

import lombok.Data;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.Map;

@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@Component
public class MyRequestBean {
    Map<String, Object> data = new HashMap<>();
}