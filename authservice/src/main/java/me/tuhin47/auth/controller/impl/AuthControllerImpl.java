package me.tuhin47.auth.controller.impl;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrDataFactory;
import dev.samstevens.totp.qr.QrGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.config.CurrentUser;
import me.tuhin47.auth.controller.AuthController;
import me.tuhin47.auth.exception.UserAlreadyExistAuthenticationException;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.mapper.UserMapper;
import me.tuhin47.auth.payload.request.LoginRequest;
import me.tuhin47.auth.payload.request.SignUpRequest;
import me.tuhin47.auth.payload.response.ApiResponse;
import me.tuhin47.auth.payload.response.JwtAuthenticationResponse;
import me.tuhin47.auth.payload.response.SignUpResponse;
import me.tuhin47.auth.service.UserService;
import me.tuhin47.auth.util.GeneralUtils;
import me.tuhin47.config.AppProperties;
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
import java.util.Collections;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final QrDataFactory qrDataFactory;
    private final QrGenerator qrGenerator;
    private final CodeVerifier verifier;
    private final UserMapper userMapper;

    @Override
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), new String(loginRequest.getPassword())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserRedis localUser = (UserRedis) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getJwtAuthenticationResponse(localUser));
    }


    @Override
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            User user = userService.registerNewUser(signUpRequest);
            if (signUpRequest.isUsing2FA()) {
                QrData data = qrDataFactory.newBuilder()
                                           .label(user.getEmail())
                                           .secret(user.getSecret())
                                           .issuer(appProperties.getConfig().getQrIssuer())
                                           .build();
                // Generate the QR code image data as a base64 string which can
                // be used in an <img> tag:
                String qrCodeImage = getDataUriForImage(qrGenerator.generate(data), qrGenerator.getImageMimeType());
                return ResponseEntity.ok().body(new SignUpResponse(true, qrCodeImage));
            }
        } catch (UserAlreadyExistAuthenticationException e) {
            log.error("Exception Occurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        } catch (QrGenerationException e) {
            log.error("QR Generation Exception Occurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Unable to generate QR code!"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
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
        return ResponseEntity.ok(userService.getJwtAuthenticationResponse(userRedis));
    }

    @Override
    @GetMapping(value = "/users/summaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllUserSummaries(@CurrentUser UserRedis userRedis) {
        log.info("retrieving all users summaries");

        return ResponseEntity.ok(userService.findAll()
                                            .stream()
                                            .filter(user -> !user.getEmail().equals(userRedis.getUsername()))
                                            .map(userMapper::toUserRedis)
                                            .map(user -> GeneralUtils.buildUserInfo(user, Collections.emptyList())));
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<?> getContent() {
        return ResponseEntity.ok("Public content goes here");
    }

    @Override
    @GetMapping("/user")
    public ResponseEntity<?> getUserContent() {
        return ResponseEntity.ok("User content goes here");
    }

    @Override
    @GetMapping("/admin")
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok("Admin content goes here");
    }

    @Override
    @GetMapping("/mod")
    public ResponseEntity<?> getModeratorContent() {
        return ResponseEntity.ok("Moderator content goes here");
    }
}
