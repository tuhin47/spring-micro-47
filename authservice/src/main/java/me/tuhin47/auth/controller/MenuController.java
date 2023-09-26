package me.tuhin47.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.tuhin47.auth.payload.response.MenuData;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Api(value = "Menu API", tags = "MENU" ,description = "Operations related to get menus")
public interface MenuController {

    @ApiOperation(value = "Get Document")
    ResponseEntity<Optional<MenuData>> getMenuData();
}
