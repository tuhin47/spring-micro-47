package me.tuhin47.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class APISecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {

        serverHttpSecurity.cors().and().csrf().disable()
//                          .exceptionHandling()
//                          .accessDeniedHandler(accessDeniedHandler)
//                          .authenticationEntryPoint(authenticationEntryPoint)
//                          .and()
                          .authorizeExchange(exchange -> exchange.anyExchange().permitAll());
        return serverHttpSecurity.build();
    }

}
