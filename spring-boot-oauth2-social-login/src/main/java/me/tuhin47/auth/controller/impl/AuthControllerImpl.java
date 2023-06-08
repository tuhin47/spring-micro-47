package me.tuhin47.auth.controller.impl;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrDataFactory;
import dev.samstevens.totp.qr.QrGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.tuhin47.auth.config.CurrentUser;
import me.tuhin47.auth.controller.AuthController;
import me.tuhin47.auth.dto.*;
import me.tuhin47.auth.exception.UserAlreadyExistAuthenticationException;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.service.UserService;
import me.tuhin47.auth.util.GeneralUtils;
import me.tuhin47.config.AppProperties;
import me.tuhin47.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@RequiredArgsConstructor
@Log4j2
@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final QrDataFactory qrDataFactory;
    private final QrGenerator qrGenerator;
    private final CodeVerifier verifier;

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        return getJwtAuthenticationResponseResponseEntity(localUser);
    }

    private ResponseEntity<JwtAuthenticationResponse> getJwtAuthenticationResponseResponseEntity(LocalUser localUser) {
        User userByEmail = userService.findUserByEmail(localUser.getEmail());
        boolean authenticated = userByEmail != null && !userByEmail.isUsing2FA();
        String jwt = tokenProvider.createToken(authenticated, localUser.getEmail());
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, authenticated, authenticated ? GeneralUtils.buildUserInfo(localUser, userByEmail) : null));
    }

    @Override
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

	@Override
	public ResponseEntity<?> verifyCode(@NotEmpty @RequestBody String code, @CurrentUser LocalUser user) {

	    User userByEmail = userService.findUserByEmail(user.getEmail());
        if (!verifier.isValidCode(userByEmail.getSecret(), code)) {
			return new ResponseEntity<>(new ApiResponse(false, "Invalid Code!"), HttpStatus.BAD_REQUEST);
		}
		String jwt = tokenProvider.createToken(true, user.getEmail());
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, true, GeneralUtils.buildUserInfo(user, userByEmail)));
	}

    @Override
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return getJwtAuthenticationResponseResponseEntity(user);
    }

    @Override
    public ResponseEntity<?> findAllUserSummaries(
            @CurrentUser LocalUser localUser) {
        log.info("retrieving all users summaries");

        return ResponseEntity.ok(userService
                .findAll()
                .stream()
                .filter(user -> !user.getEmail().equals(localUser.getUsername()))
                .map(user -> GeneralUtils.buildUserInfo(user, Collections.emptyList())));
    }

    @Override
    public ResponseEntity<?> getContent() {
        return ResponseEntity.ok("Public content goes here");
    }

    @Override
    public ResponseEntity<?> getUserContent() {
        return ResponseEntity.ok("User content goes here");
    }

    @Override
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok("Admin content goes here");
    }

    @Override
    public ResponseEntity<?> getModeratorContent() {
        return ResponseEntity.ok("Moderator content goes here");
    }
}
