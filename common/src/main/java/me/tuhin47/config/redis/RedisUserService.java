package me.tuhin47.config.redis;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RedisUserService implements UserDetailsService {

    private final RedisUserRepo redisUserRepo;

    public UserRedis saveLocalUser(UserRedis user) {
        return redisUserRepo.save(user);
    }

    public Optional<UserRedis> getUser(String email) {
        return redisUserRepo.findById(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username).orElse(null);
    }

    public static UserRedis getCurrentUser() {
        Object user = Optional.ofNullable(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication).map(Authentication::getPrincipal).orElse(null);
        return user instanceof UserRedis ? (UserRedis) user : null;
    }
}
