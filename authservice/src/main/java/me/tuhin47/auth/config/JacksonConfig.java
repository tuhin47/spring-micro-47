package me.tuhin47.auth.config;

import me.tuhin47.entity.security.SensitiveFieldSerializerModifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.module.SimpleModule;

@Configuration
public class JacksonConfig {
    @Bean
    public SimpleModule sensitiveFieldModule() {
        SimpleModule module = new SimpleModule();
        module.setSerializerModifier(new SensitiveFieldSerializerModifier());
        // Add deserializer modifier if necessary
        return module;
    }
}