package me.tuhin47.auth.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.mapper.UserMapper;
import me.tuhin47.auth.repo.RoleRepository;
import me.tuhin47.auth.repo.UserRepository;
import me.tuhin47.auth.security.oauth2.SocialProvider;
import me.tuhin47.config.CommonBean;
import me.tuhin47.config.redis.RedisUserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = true; // added to flyway for faster startup

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUserService redisUserService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        Role userRole = createRoleIfNotFound(Role.ROLE_USER);
        Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);
        Role modRole = createRoleIfNotFound(Role.ROLE_MODERATOR);
        createUserIfNotFound(CommonBean.ADMIN_USER_MAIL, Set.of(userRole, adminRole, modRole));
        alreadySetup = true;
    }

    @Transactional
    public void createUserIfNotFound(final String email, Set<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setDisplayName(email);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("admin@").getBytes());
            user.setRoles(roles);
            user.setProvider(SocialProvider.LOCAL.getProviderType());
            user.setEnabled(true);
            user = userRepository.save(user);
        }
        redisUserService.saveLocalUser(userMapper.toUserRedis(user));
    }

    @Transactional
    public Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = roleRepository.save(new Role(name));
        }
        return role;
    }
}
