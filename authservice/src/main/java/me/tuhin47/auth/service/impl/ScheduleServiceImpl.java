package me.tuhin47.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.tuhin47.auth.service.ScheduleService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    public static final String AUDIT_TASK_LOCK = "auditTaskLock";
    public static final int FIXED_DELAY_AUDIT_MS = 10000;
    private final RedisTemplate<String, String> redisTemplate;

    public ScheduleServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Scheduled(fixedDelay = FIXED_DELAY_AUDIT_MS)
    public void auditLastLoggedInUsers() {

        boolean lockAcquired = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(AUDIT_TASK_LOCK, "lock", FIXED_DELAY_AUDIT_MS, TimeUnit.MILLISECONDS));

        if (lockAcquired) {
            try {
                log.info("Auditing last logged-in users...");
            } finally {
//                redisTemplate.delete(AUDIT_TASK_LOCK);
            }
        } else {
            log.info("Another instance is already handling the task.");
        }
    }
}