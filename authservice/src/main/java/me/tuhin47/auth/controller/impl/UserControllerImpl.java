package me.tuhin47.auth.controller.impl;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.config.MyRequestBean;
import me.tuhin47.auth.controller.UserController;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.request.ChangeInfoRequest;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.auth.service.UserService;
import me.tuhin47.payload.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final MyRequestBean myRequestBean;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam("ids") String[] ids) {
        myRequestBean.getData().put("ids", Arrays.toString(ids));
        return new ResponseEntity<>(userService.getAllUsers(ids), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserInfo> updateUser(@PathVariable String id, @RequestBody ChangeInfoRequest user) {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}