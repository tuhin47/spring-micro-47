package me.tuhin47.auth.service;

import me.tuhin47.auth.payload.common.MenuDto;
import me.tuhin47.config.redis.UserRedis;

import java.util.Set;

public interface MenuService {

    Set<MenuDto> getMenusByUser(UserRedis userRedis);
}
