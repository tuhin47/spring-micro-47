package com.javachinna.controller;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.javachinna.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.javachinna.config.CurrentUser;
import com.javachinna.dto.ApiResponse;
import com.javachinna.dto.JwtAuthenticationResponse;
import com.javachinna.dto.LocalUser;
import com.javachinna.dto.LoginRequest;
import com.javachinna.dto.SignUpRequest;
import com.javachinna.dto.SignUpResponse;
import com.javachinna.exception.UserAlreadyExistAuthenticationException;
import com.javachinna.model.User;
import com.javachinna.security.jwt.TokenProvider;
import com.javachinna.service.UserService;
import com.javachinna.util.GeneralUtils;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrDataFactory;
import dev.samstevens.totp.qr.QrGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private QrDataFactory qrDataFactory;
	@Autowired
    private QrGenerator qrGenerator;
	@Autowired
    private CodeVerifier verifier;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		LocalUser localUser = (LocalUser) authentication.getPrincipal();
        return getJwtAuthenticationResponseResponseEntity(localUser);
    }

    private ResponseEntity<JwtAuthenticationResponse> getJwtAuthenticationResponseResponseEntity(LocalUser localUser) {
        boolean authenticated = !localUser.getUser().isUsing2FA();
        String jwt = tokenProvider.createToken(localUser, authenticated);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, authenticated, authenticated ? GeneralUtils.buildUserInfo(localUser) : null));
    }

    @PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			User user = userService.registerNewUser(signUpRequest);
			if (signUpRequest.isUsing2FA()) {
				QrData data = qrDataFactory.newBuilder().label(user.getEmail()).secret(user.getSecret()).issuer(appProperties.getConfig().getQrIssuer()).build();
				// Generate the QR code image data as a base64 string which can
				// be used in an <img> tag:
				String qrCodeImage = getDataUriForImage(qrGenerator.generate(data), qrGenerator.getImageMimeType());
				return ResponseEntity.ok().body(new SignUpResponse(true, qrCodeImage));
			}
		} catch (UserAlreadyExistAuthenticationException e) {
			log.error("Exception Ocurred", e);
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		} catch (QrGenerationException e) {
			log.error("QR Generation Exception Ocurred", e);
			return new ResponseEntity<>(new ApiResponse(false, "Unable to generate QR code!"), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
	}

	@PostMapping("/verify")
	@PreAuthorize("hasRole('PRE_VERIFICATION_USER')")
	public ResponseEntity<?> verifyCode(@NotEmpty @RequestBody String code, @CurrentUser LocalUser user) {
		if (!verifier.isValidCode(user.getUser().getSecret(), code)) {
			return new ResponseEntity<>(new ApiResponse(false, "Invalid Code!"), HttpStatus.BAD_REQUEST);
		}
		String jwt = tokenProvider.createToken(user, true);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, true, GeneralUtils.buildUserInfo(user)));
	}

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return getJwtAuthenticationResponseResponseEntity(user);
    }

    @GetMapping(value = "/users/summaries", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> findAllUserSummaries(
            @CurrentUser LocalUser localUser) {
        log.info("retrieving all users summaries");

        return ResponseEntity.ok(userService
                .findAll()
                .stream()
                .filter(user -> !user.getEmail().equals(localUser.getUsername()))
                .map(user -> GeneralUtils.buildUserInfo(user, Collections.emptyList())));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getContent() {
        return ResponseEntity.ok("Public content goes here");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserContent() {
        return ResponseEntity.ok("User content goes here");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok("Admin content goes here");
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getModeratorContent() {
        return ResponseEntity.ok("Moderator content goes here");
    }
}
