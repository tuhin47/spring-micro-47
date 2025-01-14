package me.tuhin47.auth.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.service.impl.UserServiceImpl;
import me.tuhin47.config.redis.RedisUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DelegatingUserDetailsService implements UserDetailsService {

    private final RedisUserService redisUserService;
    private final UserServiceImpl userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return redisUserService.getUser(username).orElseGet(() -> userServiceImpl.loadUserByUsername(username));
    }
}
