package me.tuhin47.auth.controller.impl;

import dev.samstevens.totp.code.CodeVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.auth.controller.AuthController;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.mapper.UserMapper;
import me.tuhin47.auth.payload.request.LoginRequest;
import me.tuhin47.auth.payload.request.SignUpRequest;
import me.tuhin47.auth.payload.response.*;
import me.tuhin47.auth.service.MenuService;
import me.tuhin47.auth.service.UserService;
import me.tuhin47.auth.util.GeneralUtils;
import me.tuhin47.config.annotations.CurrentUser;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final MenuService menuService;
    private final TokenProvider tokenProvider;
    private final CodeVerifier verifier;
    private final UserMapper userMapper;

    @Override
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
            new String(loginRequest.getPassword())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(userService.getJwtAuthenticationResponse(loginRequest.getEmail()));
    }

    @Override
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User user = userService.registerNewUser(signUpRequest);
        UserInfo userInfo = userMapper.toUserInfo(user);
        return new ResponseEntity<>(new SignUpResponse(user.isUsing2FA(), null, userInfo), HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@NotEmpty @RequestBody String code, @CurrentUser UserRedis userRedis) {

        if (!verifier.isValidCode(userRedis.getSecret(), code)) {
            return new ResponseEntity<>(new ApiResponse(false, "Invalid Code!"), HttpStatus.BAD_REQUEST);
        }
        String jwt = tokenProvider.createToken(true, userRedis.getEmail());
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, true, GeneralUtils.buildUserInfo(userRedis)));
    }

    @Override
    @GetMapping("/user/me")
    public ResponseEntity<?> getCurrentUser(@CurrentUser UserRedis userRedis) {
        return ResponseEntity.ok(userService.getJwtAuthenticationResponse(userRedis.getEmail()));
    }

    @Override
    @GetMapping(value = "/users/summaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stream<UserInfo>> findAllUserSummaries(@CurrentUser UserRedis userRedis) {
        log.info("retrieving all users summaries");

        return ResponseEntity.ok(userService.findAll()
                                            .stream()
                                            .map(userMapper::toUserInfo));
    }

    @Override
    public ResponseEntity<Set<MenuDto>> getAuthMenus(@CurrentUser UserRedis userRedis) {
        return new ResponseEntity<>(menuService.getMenusByUser(userRedis), HttpStatus.OK);
    }
}
