package com.microservice.orderservice.config;

import com.google.common.net.HttpHeaders;
import com.microservice.orderservice.external.decoder.CustomErrorDecoder;
import feign.*;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.*;
import org.springframework.web.context.request.*;
import java.util.Objects;
import java.util.regex.*;

@Configuration
public class FeignConfig {
    private static final Pattern BEARER_TOKEN_HEADER_PATTERN = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);
    
    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
    
    @Bean
    public RequestInterceptor requestInterceptor ()
    {
        return FeignConfig::addAuthorizationToken;
    }
    
    public static void addAuthorizationToken (Object template)
    {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        if (Objects.nonNull(requestAttributes))
        {
            
            String  authorizationHeader = requestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
            Matcher matcher             = BEARER_TOKEN_HEADER_PATTERN.matcher(authorizationHeader);
        
            if (!matcher.matches())
            {
                return;
            }
        
            if (template instanceof RequestTemplate)
            {
                ((RequestTemplate) template).header(HttpHeaders.AUTHORIZATION);
                ((RequestTemplate) template).header(HttpHeaders.AUTHORIZATION, authorizationHeader);
            }
            else if (template instanceof org.springframework.http.HttpHeaders)
            {
                ((org.springframework.http.HttpHeaders)template).add(HttpHeaders.AUTHORIZATION,authorizationHeader);
            }
        }
    }
    
}
