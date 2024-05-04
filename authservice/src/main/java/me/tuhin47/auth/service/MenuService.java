package me.tuhin47.auth.service;

import me.tuhin47.auth.payload.response.MenuData;

import java.util.Set;

public interface MenuService {

    Set<MenuData> getMenus(long id);
}
