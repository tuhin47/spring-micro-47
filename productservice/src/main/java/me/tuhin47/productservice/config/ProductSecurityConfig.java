package me.tuhin47.productservice.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.config.exception.JWTAccessDeniedHandler;
import me.tuhin47.config.exception.RestAuthenticationEntryPoint;
import me.tuhin47.jwt.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ProductSecurityConfig {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAccessDeniedHandler accessDeniedHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final String[] whiteList;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors(AbstractHttpConfigurer::disable)
                   .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .csrf(AbstractHttpConfigurer::disable)
                   .formLogin(AbstractHttpConfigurer::disable)
                   .httpBasic(AbstractHttpConfigurer::disable)
                   .authorizeHttpRequests(r -> r.requestMatchers(whiteList).permitAll())
                   .authorizeHttpRequests(r -> r.anyRequest().authenticated())
                   .exceptionHandling(configurer -> configurer.authenticationEntryPoint(authenticationEntryPoint)
                                                              .accessDeniedHandler(accessDeniedHandler)
                   ).addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

}
