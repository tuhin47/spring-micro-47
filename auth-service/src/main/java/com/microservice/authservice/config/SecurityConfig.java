package com.microservice.authservice.config;

import com.microservice.authservice.jwt.AuthTokenFilter;
import com.microservice.authservice.jwt.JWTAccessDeniedHandler;
import com.microservice.authservice.jwt.JwtAuthenticationEntryPoint;
import com.microservice.authservice.jwt.JwtUtils;
import com.microservice.authservice.oauth2.CustomOAuth2UserService;
import com.microservice.authservice.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.microservice.authservice.oauth2.OAuth2AuthenticationFailureHandler;
import com.microservice.authservice.oauth2.OAuth2AuthenticationSuccessHandler;
import com.microservice.authservice.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAccessDeniedHandler accessDeniedHandler;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http
                .cors()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                    .authorizeRequests()
                    .antMatchers("/authenticate/**", "/oauth2/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login(
                            oauth2 -> oauth2
                                    .authorizationEndpoint()
                                    .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                                    .and()
                                    .userInfoEndpoint()
                                    .userService(customOAuth2UserService)
                                    .and()
                                    .successHandler(oAuth2AuthenticationSuccessHandler)
                                    .failureHandler(oAuth2AuthenticationFailureHandler)
                    )
                    .addFilterBefore(authenticationJwtTokenFilter(jwtUtils,customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        return new AuthTokenFilter(jwtUtils, customUserDetailsService);
    }
}
