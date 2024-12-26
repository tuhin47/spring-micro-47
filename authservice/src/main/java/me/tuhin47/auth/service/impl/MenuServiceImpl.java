package me.tuhin47.auth.service.impl;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.model.Menu;
import me.tuhin47.auth.payload.mapper.MenuMapper;
import me.tuhin47.auth.payload.response.MenuDto;
import me.tuhin47.auth.repo.MenuRepository;
import me.tuhin47.auth.service.MenuService;
import me.tuhin47.config.redis.UserRedis;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class MenuServiceImpl implements MenuService {


    public static final long ROOT_ID = -1;
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    public Set<MenuDto> getMenusByUser(UserRedis userRedis) {

        return menuRepository.getMenuFromParent(ROOT_ID)
                             .map(Menu::getChildren).orElse(Collections.emptySet())
                             .stream().map(menuMapper::toDto).collect(Collectors.toSet());
    }

}
