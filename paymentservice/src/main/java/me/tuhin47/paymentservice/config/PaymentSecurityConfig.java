package me.tuhin47.paymentservice.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.config.exception.JWTAccessDeniedHandler;
import me.tuhin47.config.exception.RestAuthenticationEntryPoint;
import me.tuhin47.jwt.CustomBearerTokenResolver;
import me.tuhin47.jwt.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class PaymentSecurityConfig {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAccessDeniedHandler accessDeniedHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final String[] whiteList;

    interface AuthoritiesConverter extends Converter<Map<String, Object>, Collection<GrantedAuthority>> {
    }

    @Bean
    AuthoritiesConverter realmRolesAuthoritiesConverter() {
        return claims -> {
            final var realmAccess = Optional.ofNullable((Map<String, Object>) claims.get("realm_access"));
            final var roles =
                realmAccess.flatMap(map -> Optional.ofNullable((List<String>) map.get("roles")));
            return roles.stream().flatMap(Collection::stream).map(SimpleGrantedAuthority::new)
                        .map(GrantedAuthority.class::cast).toList();
        };
    }

    @Bean
    JwtAuthenticationConverter authenticationConverter(
        Converter<Map<String, Object>, Collection<GrantedAuthority>> authoritiesConverter) {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter
            .setJwtGrantedAuthoritiesConverter(jwt -> authoritiesConverter.convert(jwt.getClaims()));
        return jwtAuthenticationConverter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
                                    Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter) throws Exception {

        return http.cors(AbstractHttpConfigurer::disable)
                   .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .csrf(AbstractHttpConfigurer::disable)
                   .formLogin(AbstractHttpConfigurer::disable)
                   .httpBasic(AbstractHttpConfigurer::disable)
                   .securityMatchers(r -> {
                       r.requestMatchers("/**");
                   })
                   .authorizeHttpRequests(r -> {
                       r.requestMatchers(whiteList).permitAll();
                       r.anyRequest().authenticated();
                   })
                   .exceptionHandling(configurer -> configurer.authenticationEntryPoint(authenticationEntryPoint)
                                                              .accessDeniedHandler(accessDeniedHandler))
                   .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                   .oauth2ResourceServer(resourceServer ->
                       resourceServer
                           .bearerTokenResolver(new CustomBearerTokenResolver(new DefaultBearerTokenResolver()))
                           .jwt(jwtDecoder -> jwtDecoder.jwtAuthenticationConverter(jwtAuthenticationConverter))

                   )
                   .build();
    }

}
