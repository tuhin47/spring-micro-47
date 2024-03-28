package me.tuhin47.auth.service.impl;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.payload.response.MenuData;
import me.tuhin47.auth.repo.MenuRepository;
import me.tuhin47.auth.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {


    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public Optional<MenuData> getMenuData(long id) {
        return menuRepository.getAllMenuDataFromRoot(id);
    }
}
