package me.tuhin47.auth.service;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.security.oauth2.LocalUser;
import me.tuhin47.auth.util.GeneralUtils;
import me.tuhin47.config.RedisUser;
import me.tuhin47.config.RedisUserService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Chinna
 */
@Service
@Primary
@RequiredArgsConstructor
public class LocalUserDetailService implements UserDetailsService {

    private final UserService userService;
    private final RedisUserService redisUserService;

    @Override
    @Transactional
    public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }

        LocalUser localUser = createLocalUser(user);

        redisUserService.saveRedisUser(RedisUser.builder()
                                                .userId(user.getId())
                                                .name(user.getDisplayName())
                                                .id(user.getEmail())
                                                .authorities(localUser.getAuthorities())
                                                .password(new String(user.getPassword()))
                                                .build());

        return localUser;
    }

    private LocalUser createLocalUser(User user) {
        return new LocalUser(user.getEmail(), new String(user.getPassword()), user.isEnabled(), true, true, true,
            GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user.getEmail());
    }
}
