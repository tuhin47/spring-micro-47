package me.tuhin47.auth.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.auth.controller.MenuController;
import me.tuhin47.auth.payload.response.MenuData;
import me.tuhin47.auth.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/auth/menu")
public class MenuControllerImpl implements MenuController {

    public static final int ROOT_ID = -1;
    private final MenuService menuService;

    @Override
    @GetMapping
    public ResponseEntity<Set<MenuData>> getMenuData() {
        return new ResponseEntity<>(menuService.getMenus(ROOT_ID), HttpStatus.OK);
    }

}
