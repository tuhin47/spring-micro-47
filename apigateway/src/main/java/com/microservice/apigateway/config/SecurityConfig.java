package com.microservice.apigateway.config;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {

        serverHttpSecurity.cors().and().csrf().disable()
                .authorizeExchange(exchange -> exchange
                        .anyExchange()
                        .permitAll());
        return serverHttpSecurity.build();
    }

   /* @Bean
    WebFilter traceIdInResponseFilter(Tracer tracer) {
        return (exchange, chain) -> {
            Span span = tracer.currentSpan();
            if (span != null) {
                exchange.getResponse().getHeaders().add("trace-id", span.context().traceId());
                exchange.getResponse().getHeaders().add("span-id", span.context().spanId());
            }
            return chain.filter(exchange);
        };
    }*/



}
