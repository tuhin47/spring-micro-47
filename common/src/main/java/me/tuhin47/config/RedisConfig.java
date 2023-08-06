package me.tuhin47.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "me.tuhin47.config.redis")
@RequiredArgsConstructor
public class RedisConfig {

}
