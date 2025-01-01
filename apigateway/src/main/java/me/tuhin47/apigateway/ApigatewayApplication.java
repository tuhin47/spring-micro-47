package me.tuhin47.apigateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import me.tuhin47.config.AppProperties;
import me.tuhin47.jwt.TokenProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;

@SpringBootApplication(scanBasePackages = {"me.tuhin47.apigateway"})
//@EnableEurekaClient
@Import({AppProperties.class, TokenProvider.class})
public class ApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(
            id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()).build()
        );
    }

    @Bean
    KeyResolver userKeySolver() {
        return exchange -> Mono.just("userKey");
    }

}
