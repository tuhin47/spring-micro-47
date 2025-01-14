package me.tuhin47.auth.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.security.oauth2.*;
import me.tuhin47.config.exception.JWTAccessDeniedHandler;
import me.tuhin47.config.exception.RestAuthenticationEntryPoint;
import me.tuhin47.jwt.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestClient;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class AuthSecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final String[] whiteList;
    private final JWTAccessDeniedHandler accessDeniedHandler;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;
    private final DelegatingUserDetailsService delegatingUserDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.cors(AbstractHttpConfigurer::disable)
                   .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .csrf(AbstractHttpConfigurer::disable)
                   .formLogin(AbstractHttpConfigurer::disable)
                   .httpBasic(AbstractHttpConfigurer::disable)
//                   .authorizeHttpRequests(r -> r.anyRequest().authenticated())
                   .authorizeHttpRequests(r -> r.requestMatchers(whiteList).permitAll())
                   .authorizeHttpRequests(r -> r.requestMatchers("/", "/error", "/api/all", "/auth/*", "/oauth2/**").permitAll())
                   .authorizeHttpRequests(r -> r.anyRequest().authenticated())
                   .exceptionHandling(configurer -> configurer.authenticationEntryPoint(authenticationEntryPoint)
                                                              .accessDeniedHandler(accessDeniedHandler)
                   )
                   .oauth2Login(
                       loginConfigurer -> loginConfigurer.authorizationEndpoint(config -> config.authorizationRequestRepository(cookieAuthorizationRequestRepository()))
                                                         //                    .redirectionEndpoint(redirectionEndpointConfig -> )
                                                         .userInfoEndpoint(
                                                             userInfoEndpointConfig -> userInfoEndpointConfig.oidcUserService(customOidcUserService)
                                                                                                             .userService(customOAuth2UserService)
                                                         ).tokenEndpoint(tokenEndpointConfig ->
                               tokenEndpointConfig.accessTokenResponseClient(authorizationCodeTokenResponseClient())
                           ).successHandler(oAuth2AuthenticationSuccessHandler)
                                                         .failureHandler(oAuth2AuthenticationFailureHandler)
                   ).addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();

    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(delegatingUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> authorizationCodeTokenResponseClient() {
        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        tokenResponseHttpMessageConverter.setAccessTokenResponseConverter(new OAuth2AccessTokenResponseConverterWithDefaults());
        RestClient client = RestClient.builder().messageConverters((messageConverters) -> {
            messageConverters.clear();
            messageConverters.add(new FormHttpMessageConverter());
            messageConverters.add(tokenResponseHttpMessageConverter);
        }).defaultStatusHandler(new OAuth2ErrorResponseErrorHandler()).build();
        RestClientAuthorizationCodeTokenResponseClient tokenResponseClient = new RestClientAuthorizationCodeTokenResponseClient();
        tokenResponseClient.setRestClient(client);
        return tokenResponseClient;
    }
}
