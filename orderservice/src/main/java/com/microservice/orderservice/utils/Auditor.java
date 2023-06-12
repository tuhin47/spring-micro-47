package com.microservice.orderservice.utils;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class Auditor implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        /*SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();*/

        return Optional.ofNullable(1L);
//        return authentication != null ? Optional.of((String) authentication.getPrincipal()) : Optional.of("0");
    }

}