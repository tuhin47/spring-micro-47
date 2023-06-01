package me.tuhin47.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisUserService implements UserDetailsService {

    private final RedisUserRepo redisUserRepo;
//    private final RedisTemplate<String, RedisUser> redisTemplate;


    public void saveRedisUser(RedisUser user) {
//        redisTemplate.opsForValue().setIfAbsent(user.getUsername(), user);
        redisUserRepo.save(user);
    }

    public RedisUser getUser(String email) {
//        return redisTemplate.opsForValue().get(email);
        return redisUserRepo.findById(email).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }
}
