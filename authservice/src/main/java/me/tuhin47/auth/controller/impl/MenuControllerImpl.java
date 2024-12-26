package me.tuhin47.auth.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.auth.controller.MenuController;
import me.tuhin47.auth.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuControllerImpl implements MenuController {

    private final MenuService menuService;
    // Rest of the controller methods...
    

}
