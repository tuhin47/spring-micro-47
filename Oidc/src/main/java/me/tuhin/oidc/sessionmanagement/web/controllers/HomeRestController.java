package me.tuhin.oidc.sessionmanagement.web.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeRestController {

    @GetMapping("/home")
    public String simpleHomepage() {
        return "Welcome to this simple homepage!";
    }

}
