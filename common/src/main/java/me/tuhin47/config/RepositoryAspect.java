package me.tuhin47.config;

import lombok.extern.slf4j.Slf4j;
import me.tuhin47.entity.security.IOwner;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Aspect
@Component
@Slf4j
public class RepositoryAspect {

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    public void repositoryMethods() {
    }

    @Around("repositoryMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            // TODO : if result is an object of IOwner interface or a collection of IOwner interface then filter based on owner

            if (result instanceof IOwner owner && !owner.isOwner()) {
                log.info("don't have access for object {}", result);
                return null;
            } else {
                if (result instanceof Collection<?> collection) {
                    Optional<?> first = collection.stream().findFirst();
                    if (first.isPresent() && first.get() instanceof IOwner) {
                        log.info("filtering collection");

                        return collection.stream()
                                         .filter(item -> !(item instanceof IOwner owner) || owner.isOwner())
                                         .collect(Collectors.toList());
                    }
                }
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}
