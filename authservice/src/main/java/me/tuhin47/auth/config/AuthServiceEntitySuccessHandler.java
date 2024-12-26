package me.tuhin47.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.mapper.UserMapper;
import me.tuhin47.config.redis.RedisUserRepo;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.entity.BaseEntity;
import me.tuhin47.entity.EntityStatus;
import me.tuhin47.entity.EntitySuccessEvent;
import me.tuhin47.entity.EntitySuccessHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceEntitySuccessHandler implements EntitySuccessHandler {

    private final RedisUserRepo redisUserRepo;
    private final UserMapper userMapper;

    @Override
    @Async("taskExecutor")
    public void executeTask(final EntitySuccessEvent event) {
        log.info("Executing entity success Entity{}", event);
        BaseEntity<?> entity = event.getEntity();
        if (entity instanceof User user) {
            manageUserRedis(user, event);
        } else if (entity instanceof Privilege privilege) {
            privilege.getUsers().forEach(u -> manageUserRedis(u, event));
        } else if (entity instanceof Role role) {
            role.getUsers().forEach(u -> manageUserRedis(u, event));
        }
    }

    private void manageUserRedis(User user, EntitySuccessEvent event) {
        Optional<UserRedis> optional = redisUserRepo.findById(user.getEmail());
        if (event.getStatus() == EntityStatus.DELETED && event.getEntity() instanceof User) {
            optional.ifPresent(redisUserRepo::delete);
            return;
        }
        optional.ifPresent(u -> redisUserRepo.save(userMapper.toUserRedis(user)));
    }
}
