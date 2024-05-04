package me.tuhin47.auth.service.impl;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.payload.response.MenuData;
import me.tuhin47.auth.repo.MenuRepository;
import me.tuhin47.auth.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {


    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public Set<MenuData> getMenus(long id) {
        return menuRepository.getAllMenuDataFromRoot(id).map(MenuData::getChildren).orElse(Collections.emptySet());
    }
}
