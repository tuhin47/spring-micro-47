package me.tuhin47.entity.audit;

import me.tuhin47.config.redis.RedisUserService;
import me.tuhin47.config.redis.UserRedis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditAwareImpl();
    }
}

class SpringSecurityAuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        UserRedis userPrincipal = RedisUserService.getCurrentUser();
        return Optional.ofNullable(userPrincipal).map(UserRedis::getUserId).or(Optional::empty);
    }
}