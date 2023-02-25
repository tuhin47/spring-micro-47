package com.javachinna.controller;

import com.javachinna.config.CurrentUser;
import com.javachinna.controller.dto.UserSummary;
import com.javachinna.dto.LocalUser;
import com.javachinna.exception.ResourceNotFoundException;
import com.javachinna.model.User;
import com.javachinna.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/auth")
public class UserEndpoint {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        log.info("retrieving all users");

        return ResponseEntity
                .ok(userService.findAll());
    }

    @GetMapping(value = "/users/summaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllUserSummaries(
            @CurrentUser LocalUser localUser) {
        log.info("retrieving all users summaries");

        return ResponseEntity.ok(userService
                .findAll()
                .stream()
                .filter(user -> !user.getEmail().equals(localUser.getUsername()))
                .map(this::convertTo));
    }

    private UserSummary convertTo(User user) {
        return UserSummary
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getDisplayName())
                .avatar("https://bootdey.com/img/Content/avatar/avatar1.png")
                .build();
    }
}
