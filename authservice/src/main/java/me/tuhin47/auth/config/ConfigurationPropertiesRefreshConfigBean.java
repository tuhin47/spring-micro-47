package me.tuhin47.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@ConfigurationProperties(prefix = "application.theme")
@RefreshScope
@Getter
@Setter
public class ConfigurationPropertiesRefreshConfigBean {
    private static final List<String> COLORS = Arrays.asList("Red", "Green", "Blue", "Yellow", "Purple", "Orange", "Pink", "Brown", "Gray", "Black", "White");
    private String color;

    public static String getRandomColor() {
        Random random = new Random();
        int index = random.nextInt(COLORS.size());
        return COLORS.get(index);
    }
}