package me.tuhin47.orderservice.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.config.security.JWTAccessDeniedHandler;
import me.tuhin47.config.security.RestAuthenticationEntryPoint;
import me.tuhin47.jwt.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class OrderSecurityConfig {

    private final JWTAccessDeniedHandler accessDeniedHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final String[] whiteList;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(whiteList).permitAll()
                .antMatchers(HttpMethod.POST, "/order/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/order/**").hasRole("USER")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
