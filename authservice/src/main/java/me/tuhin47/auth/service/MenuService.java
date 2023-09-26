package me.tuhin47.auth.service;

import me.tuhin47.auth.payload.response.MenuData;

import java.util.Optional;

public interface MenuService {

    Optional<MenuData> getMenuData(long id);
}
